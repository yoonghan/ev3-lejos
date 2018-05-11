package com.walcron.lego.test

case class User(val username:String, val password:String)



trait UserRepositoryComponent {
  val userRepository = new UserRepository
  class UserRepository{
    def authenticate(user:User):User = {
      println("authenticate")
      user
    }
    
    def create(user:User) = println("create")
    def delete(user:User) = println("delete")
  }
}

trait UserServiceComponent {this:UserRepositoryComponent => 
  val userService:UserService
  class UserService {
    def authenticate(username:String, password:String):User = 
      userRepository.authenticate(User(username, password))
    
    def create(username:String, password:String) = 
      userRepository.create(User(username, password))
  }
}

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  val userService = new UserService
}