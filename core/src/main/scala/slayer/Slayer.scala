package slayer

import zio.*

/** Public API surface for slayer.
  *
  * `stub` registers a stub for one method of a service trait. The selector lambda picks the method. The returned
  * builder's `apply` expects a value of the method's return type, or a function of the method's value parameters
  * (tupled or curried). Those parameter types are the function's expected types, so a handler lambda needs no
  * ascriptions.
  *
  * `stubbed` / `stubbedTraced` synthesize a layer providing a `Service & Stubbed[Service]` whose method bodies look up
  * `Stubbed`'s store. Use `stubbedTraced` when you want a structured [[Call]] log.
  */
transparent inline def stub[Service](inline select: Service => Any): Any =
  ${ Macros.stubPartial[Service]('select) }

/** Synthesize a `ZLayer` providing a runtime stub for `Service` (no call recording).
  *
  * The macro emits a class extending both `Service` (with every declared method delegating to `Stubbed.callStubbed`)
  * and `Stubbed[Service]` (so `stub` calls can store entries in the same instance). Use it at the top of a test:
  *
  * ```
  * val layer: ULayer[Repo & Stubbed[Repo]] = stubbed[Repo]
  * ```
  *
  * Then `stub[Repo](_.find(any))(...)` inside an effect provided with this layer registers a stub that the synthesized
  * class returns when `Repo.find` is called.
  */
inline def stubbed[Service]: ULayer[Service & Stubbed[Service]] =
  ${ Macros.stubbedImpl[Service](false) }

/** Like [[stubbed]], but enables call recording on the synthesized instance.
  *
  * Every method invoke appends a [[Call]] (method id + args). Results are returned unchanged — use
  * `effect.debug("label")` yourself if you want console tracing. Inspect history with
  * `ZIO.serviceWith[Stubbed[S]](_.calls)`.
  */
inline def stubbedTraced[Service]: ULayer[Service & Stubbed[Service]] =
  ${ Macros.stubbedImpl[Service](true) }
