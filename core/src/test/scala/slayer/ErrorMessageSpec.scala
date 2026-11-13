package slayer

import zio.*
import zio.test.*

/** Compile-time failures of `stub` and `stubbed`.
  *
  * A bad handler is a typer error on the builder's `apply`: the message names the method's function type and the type
  * that was passed. A bad selector is still the macro's own message.
  */
object ErrorMessageSpec extends ZIOSpecDefault:

  trait Svc:
    def find(id: Int): String
    def label: String
    def merge(a: Int, b: Int): String

  def spec = suite("ErrorMessageSpec — M7 compile-error contracts")(
    test("result type doesn't match method return type — names both types") {
      for res <- typeCheck("""
        import slayer.*
        stub[ErrorMessageSpec.Svc](_.find(any[Int]))(42)
      """)
      yield
        val msg = res.swap.getOrElse("")
        assertTrue(
          res.isLeft,
          msg.contains("String"),
          msg.contains("Int"),
          msg.contains("match arguments"),
        )
    },
    test("selector isn't a method — message identifies the problem") {
      for res <- typeCheck("""
        import slayer.*
        stub[ErrorMessageSpec.Svc](_ => 42)("x")
      """)
      yield
        val msg = res.swap.getOrElse("")
        assertTrue(
          res.isLeft,
          msg.contains("selector"),
          msg.contains("method"),
        )
    },
    test("handler arity doesn't match — names the method's function and the handler") {
      for res <- typeCheck("""
        import slayer.*
        stub[ErrorMessageSpec.Svc](_.merge(any[Int], any[Int]))((a: Int) => "x")
      """)
      yield
        val msg = res.swap.getOrElse("")
        assertTrue(
          res.isLeft,
          msg.contains("(Int, Int) => String"),
          msg.contains("Int => String"),
        )
    },
    test("handler param type incompatible — message names mismatched types") {
      for res <- typeCheck("""
        import slayer.*
        stub[ErrorMessageSpec.Svc](_.find(any[Int]))((s: String) => "x")
      """)
      yield
        val msg = res.swap.getOrElse("")
        assertTrue(
          res.isLeft,
          msg.contains("Int => String"),
          msg.contains("String => String"),
        )
    },
    test("handler return type incompatible — message names both") {
      for res <- typeCheck("""
        import slayer.*
        stub[ErrorMessageSpec.Svc](_.find(any[Int]))((i: Int) => 42)
      """)
      yield
        val msg = res.swap.getOrElse("")
        assertTrue(
          res.isLeft,
          msg.contains("Int => String"),
          msg.contains("Int => Int"),
        )
    },
  )
end ErrorMessageSpec
