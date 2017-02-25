package com.github.johnreedlol.conversions

import com.github.johnreedlol.Debug
import com.github.johnreedlol.internal.Printer

import scala.language.implicitConversions

/**
  * Wrapper class for implicit conversion .assert methods
  */
final case class ImplicitAssert[MyType](val me: MyType) extends AnyVal {

  /** A fatal assertion, but with the function name after the object.
    * Terminates the program with exit code "7"
    *
    * @param assertion the assertion that must be true for the program to run
    * @param message   the message to be printed to standard error on assertion failure
    * @param numLines  the number of lines of stack trace
    * @example 1.assert( _ + 2 = 3, "Error: one plus two does not equal three.")
    * @note this (and other assertions not marked "nonFatal") are fatal. To disable, please call "Debug.fatalAssertOffSE()"
    */
  final def assert(assertion: (MyType) => Boolean, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, assertionTrue_? = assertion(me), isFatal_? = true)
    me
  }

  /** A fatal assertion, but with the function name after the object.
    * Terminates the program with exit code "7"
    *
    * @param assertion the assertion that must be true for the program to run
    * @param message   the message to be printed  to standard out on assertion failure
    * @param numLines  the number of lines of stack trace
    * @example 1.assertStdOut( _ + 2 = 3, "Error: one plus two does not equal three.")
    * @note this (and other assertions not marked "nonFatal") are fatal. To disable, please call "Debug.fatalAssertOffSE()"
    */
  final def assertOut(assertion: (MyType) => Boolean, message: String, numLines: Int = Int.MaxValue): MyType = {
    if (!assertion(me) && Debug.isFatalAssertOn) {
      Printer.internalAssert(message, numLines, usingStdOut = true, assertionTrue_? = assertion(me), isFatal_? = true)
      System.exit(7)
    }
    me
  }

  /** Compares this object with another object of the same type for equality using the ".equals()" method.
    * Terminates the program with exit code "7" in case of assertion failure
    *
    * @param other    the thing that this must be equal to
    * @param message  the message to be printed  to standard error on assertion failure
    * @param numLines the max number of lines of stack trace to show on assertion failure. Defaults to all lines
    * @example "foo".assertEquals("bar", "Error: foo does not equal bar")
    * @note this (and other assertions not marked "nonFatal") are fatal. To disable, please call "Debug.fatalAssertOffSE()"
    */
  final def assertEq[OtherType](other: OtherType, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = false, assertionTrue_? = me.equals(other), isFatal_? = true)
    me
  }

  /**
    * Same as ImplicitTrace.assertEq(), but it uses StdOut instead of StdErr.
    *
    * @note this (and other assertions not marked "nonFatal") are fatal. To disable, please call "Debug.fatalAssertOffSE()"
    */
  final def assertEqOut[OtherType](other: OtherType, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = true, assertionTrue_? = me.equals(other), isFatal_? = true)
    me
  }

  /**
    * Same as ImplicitTrace[MyType].assert(), but it does not kill anything (not even the current thread)
    */
  final def check(assertion: (MyType) => Boolean, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = false, assertionTrue_? = assertion(me), isFatal_? = false)
    me
  }

  /**
    * Same as ImplicitTrace[MyType].assertOut(), but it does not kill anything (not even the current thread)
    */
  final def checkOut(assertion: (MyType) => Boolean, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = true, assertionTrue_? = assertion(me), isFatal_? = false)
    me
  }

  /**
    * Same as ImplicitTrace[MyType].assertEq(), but it does not kill anything (not even the current thread)
    */
  final def checkEq[OtherType](other: OtherType, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = false, assertionTrue_? = me.equals(other), isFatal_? = false)
    me
  }

  /**
    * Same as ImplicitTrace[MyType].assertEqOut(), but it does not kill anything (not even the current thread)
    */
  final def checkEqOut[OtherType](other: OtherType, message: String, numLines: Int = Int.MaxValue): MyType = {
    Printer.internalAssert(message, numLines, usingStdOut = true, assertionTrue_? = me.equals(other), isFatal_? = false)
    me
  }
}
