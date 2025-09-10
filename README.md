Day 1:
I have created the basic structure for the project. I created a java program called PasswordGenerator for getting a random password and copied the password to the database. This record is the pre-seeded admin with the username admin.
A database called azuredemo was created along with a user "demouser" with a password. The demouser has all the privileges and access to the azuredemo database.
JdbcUserDao.java is used to add new users or search users with their username
LoginServlet.java is used to handle login. if it is valid, it stores the users info or else displays error.
PasswordUtil.java handles password verification
