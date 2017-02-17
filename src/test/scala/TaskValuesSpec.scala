package org.typelevel.scalatest

import org.scalatest.exceptions.TestFailedException

import scalaz.concurrent.Task

class TaskValuesSpec extends TestBase {
  import TaskValues._

  "runValue on Task" should {
    "return the value inside a Task if the resulting disjunction is \\/- (right)" in {
      val r: Task[String] = Task.delay(thisRecord)
      r.runValue should ===(thisRecord)
    }

    "throw TestFailedException if the Task failed" in {
      val r: Task[String] = Task.fail(new Exception(thisTobacconist))
      val caught =
        intercept[TestFailedException] {
          r.runValue should ===(thisRecord)
        }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("TaskValuesSpec.scala")
    }
  }

  "failValue on Task" should {
    "return the value inside a Task if the resulting disjunction is -\\/ (left)" in {
      val e = new Exception(thisTobacconist)
      val r: Task[String] = Task.fail(e)
      r.failValue shouldBe e
    }

    "throw TestFailedException if the Task succeeded" in {
      val r: Task[String] = Task.delay(thisRecord)
      val caught =
        intercept[TestFailedException] {
          r.failValue should ===(thisRecord)
        }
      caught.failedCodeLineNumber.value should equal(thisLineNumber - 2)
      caught.failedCodeFileName.value should be("TaskValuesSpec.scala")
    }
  }
}

