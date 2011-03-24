package code 
package snippet 

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

import net.liftweb.http.S
import code.model.User


class LoggedIn extends Authorization {
  override def isAuthorized = User.loggedIn_?
    
}

class InRole extends Authorization {
  override def isAuthorized = (
    for (role <- S.attr("role") ;
         user <- User.currentUser)
    yield user.roles contains role) openOr false

//User.currentUser map (_.roles contains S.attr("role")) openOr false
}

trait Authorization {
  def isAuthorized: Boolean

  def render(xhtml: NodeSeq):NodeSeq =
    if(isAuthorized)
      xhtml
    else
      NodeSeq.Empty
}

