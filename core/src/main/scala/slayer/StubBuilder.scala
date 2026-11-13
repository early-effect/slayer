package slayer

import scala.DummyImplicit
import zio.*

/** The `Array[Any]` a synthesized method packs. The cast is that erasure boundary. */
private[slayer] object Arg:
  def at[A](args: Array[Any], i: Int): A = args(i).asInstanceOf[A]

/** What `stub` returns. `apply` takes a value of the method's return type, or a function of its value parameters.
  *
  * The function overloads carry `DummyImplicit` so a value that already has the return type prefers the value overload.
  * Tupled and curried overloads are different function types, so a lambda is checked against one of them. `Stub0`
  * through `Stub22` cover every tupled function Scala can write. A longer method uses [[StubHigh]], whose handler is
  * the curried chain.
  */
final class Stub0[S, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)
  def apply(handler: () => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, _ => handler())

final class Stub1[S, A, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)
  def apply(handler: A => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, args => handler(Arg.at[A](args, 0)))

final class Stub2[S, A, B, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)
  def apply(handler: (A, B) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, args => handler(Arg.at[A](args, 0), Arg.at[B](args, 1)))
  def apply(handler: A => B => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, args => handler(Arg.at[A](args, 0))(Arg.at[B](args, 1)))

final class Stub3[S, A, B, C, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, args => handler(Arg.at[A](args, 0), Arg.at[B](args, 1), Arg.at[C](args, 2)))
  def apply(handler: A => B => C => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(id, args => handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2)))

final class Stub4[S, A, B, C, D, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args => handler(Arg.at[A](args, 0), Arg.at[B](args, 1), Arg.at[C](args, 2), Arg.at[D](args, 3)),
    )
  def apply(handler: A => B => C => D => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args => handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3)),
    )
end Stub4

final class Stub5[S, A, B, C, D, E, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit]                                           = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0), Arg.at[B](args, 1), Arg.at[C](args, 2), Arg.at[D](args, 3), Arg.at[E](args, 4)),
    )
  def apply(handler: A => B => C => D => E => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4)),
    )
end Stub5

final class Stub6[S, A, B, C, D, E, F, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
        ),
    )
  def apply(handler: A => B => C => D => E => F => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        ),
    )
end Stub6

final class Stub7[S, A, B, C, D, E, F, G, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6)),
    )
end Stub7

final class Stub8[S, A, B, C, D, E, F, G, H, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
          Arg.at[H](args, 7),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6))(Arg.at[H](args, 7)),
    )
end Stub8

final class Stub9[S, A, B, C, D, E, F, G, H, I, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
          Arg.at[H](args, 7),
          Arg.at[I](args, 8),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6))(Arg.at[H](args, 7))(Arg.at[I](args, 8)),
    )
end Stub9

final class Stub10[S, A, B, C, D, E, F, G, H, I, J, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
          Arg.at[H](args, 7),
          Arg.at[I](args, 8),
          Arg.at[J](args, 9),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6))(Arg.at[H](args, 7))(Arg.at[I](args, 8))(Arg.at[J](args, 9)),
    )
end Stub10

final class Stub11[S, A, B, C, D, E, F, G, H, I, J, K, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
          Arg.at[H](args, 7),
          Arg.at[I](args, 8),
          Arg.at[J](args, 9),
          Arg.at[K](args, 10),
        ),
    )
  def apply(
      handler: A => B => C => D => E => F => G => H => I => J => K => R
  )(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6))(Arg.at[H](args, 7))(Arg.at[I](args, 8))(Arg.at[J](args, 9))(Arg.at[K](args, 10)),
    )
end Stub11

final class Stub12[S, A, B, C, D, E, F, G, H, I, J, K, L, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at[A](args, 0),
          Arg.at[B](args, 1),
          Arg.at[C](args, 2),
          Arg.at[D](args, 3),
          Arg.at[E](args, 4),
          Arg.at[F](args, 5),
          Arg.at[G](args, 6),
          Arg.at[H](args, 7),
          Arg.at[I](args, 8),
          Arg.at[J](args, 9),
          Arg.at[K](args, 10),
          Arg.at[L](args, 11),
        ),
    )
  def apply(
      handler: A => B => C => D => E => F => G => H => I => J => K => L => R
  )(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at[A](args, 0))(Arg.at[B](args, 1))(Arg.at[C](args, 2))(Arg.at[D](args, 3))(Arg.at[E](args, 4))(
          Arg.at[F](args, 5)
        )(Arg.at[G](args, 6))(Arg.at[H](args, 7))(Arg.at[I](args, 8))(Arg.at[J](args, 9))(Arg.at[K](args, 10))(
          Arg.at[L](args, 11)
        ),
    )
end Stub12

final class Stub13[S, A, B, C, D, E, F, G, H, I, J, K, L, M, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12)),
    )
end Stub13

final class Stub14[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        ),
    )
end Stub14

final class Stub15[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => R)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14)),
    )
end Stub15

final class Stub16[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15)),
    )
end Stub16

final class Stub17[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16)),
    )
end Stub17

final class Stub18[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
          Arg.at(args, 17),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => T => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16))(Arg.at(args, 17)),
    )
end Stub18

final class Stub19[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
          Arg.at(args, 17),
          Arg.at(args, 18),
        ),
    )
  def apply(handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => T => U => R)(
      using DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16))(Arg.at(args, 17))(Arg.at(args, 18)),
    )
end Stub19

final class Stub20[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
          Arg.at(args, 17),
          Arg.at(args, 18),
          Arg.at(args, 19),
        ),
    )
  def apply(
      handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => T => U => V => R
  )(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16))(Arg.at(args, 17))(Arg.at(args, 18))(Arg.at(args, 19)),
    )
end Stub20

final class Stub21[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V, W, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V, W) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
          Arg.at(args, 17),
          Arg.at(args, 18),
          Arg.at(args, 19),
          Arg.at(args, 20),
        ),
    )
  def apply(
      handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => T => U => V => W => R
  )(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16))(Arg.at(args, 17))(Arg.at(args, 18))(Arg.at(args, 19))(
          Arg.at(args, 20)
        ),
    )
end Stub21

final class Stub22[S, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V, W, X, R](id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] = Stubbed.insertValue(id, value)
  def apply(handler: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, T, U, V, W, X) => R)(using
      DummyImplicit
  ): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(
          Arg.at(args, 0),
          Arg.at(args, 1),
          Arg.at(args, 2),
          Arg.at(args, 3),
          Arg.at(args, 4),
          Arg.at(args, 5),
          Arg.at(args, 6),
          Arg.at(args, 7),
          Arg.at(args, 8),
          Arg.at(args, 9),
          Arg.at(args, 10),
          Arg.at(args, 11),
          Arg.at(args, 12),
          Arg.at(args, 13),
          Arg.at(args, 14),
          Arg.at(args, 15),
          Arg.at(args, 16),
          Arg.at(args, 17),
          Arg.at(args, 18),
          Arg.at(args, 19),
          Arg.at(args, 20),
          Arg.at(args, 21),
        ),
    )
  def apply(
      handler: A => B => C => D => E => F => G => H => I => J => K => L => M => N => O => P => Q => T => U => V => W => X => R
  )(using DummyImplicit): URIO[Stubbed[S], Unit] =
    Stubbed.insertFunc(
      id,
      args =>
        handler(Arg.at(args, 0))(Arg.at(args, 1))(Arg.at(args, 2))(Arg.at(args, 3))(Arg.at(args, 4))(Arg.at(args, 5))(
          Arg.at(args, 6)
        )(Arg.at(args, 7))(Arg.at(args, 8))(Arg.at(args, 9))(Arg.at(args, 10))(Arg.at(args, 11))(Arg.at(args, 12))(
          Arg.at(args, 13)
        )(Arg.at(args, 14))(Arg.at(args, 15))(Arg.at(args, 16))(Arg.at(args, 17))(Arg.at(args, 18))(Arg.at(args, 19))(
          Arg.at(args, 20)
        )(Arg.at(args, 21)),
    )
end Stub22

/** A method with more than 22 value parameters. Scala has no `Function23`, so the handler is curried. */
final class StubHigh[S, F, R](val id: MethodId)(using Tag[S]):
  def apply(value: R): URIO[Stubbed[S], Unit] =
    Stubbed.insertValue(id, value)

  inline def apply(inline handler: F)(using DummyImplicit): URIO[Stubbed[S], Unit] =
    ${ Macros.adaptCurried[S, F]('id, 'handler) }
