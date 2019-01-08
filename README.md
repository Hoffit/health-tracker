# CF Java 401 Lab Work
## Labs 16-19: Code Fellowship User Community
This is a Java Spring MVC Web App, spanning four labs, 16-19. Each will be laid out in a more little detail below.

###High Level
It's a web app prototype about a user community that can register users, post content, and browse fellow coders posts.
It uses Java, Spring MVC, Spring JPA, Spring Security, Gradle, PostgreSQL, Thymeleaf, and DevTools.
It was developed in IntelliJ. It is a Spring Boot app, created initially with Spring Initializr.
It's home is in github: https://github.com/Hoffit/codefellowship

## Lab 16
### Feature Tasks
#### Build an app that allows users to create their profile on CodeFellowship.
    The site should have a splash page at the root route (/) that contains basic information about the site, as well as a link to the “sign up” page.
    An ApplicationUser should have a username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth, bio, and any other fields you think are useful.
    The site should allow users to create an ApplicationUser on the “sign up” page. (For now, there is no such thing as login.)
        Your Controller should have an @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder; and use that to run bCryptPasswordEncoder.encode(password) before saving the password into the new user.
    The site should have a page which allows viewing the data about a single ApplicationUser, at a route like /users/{id}.
        This should include a default profile picture, which is the same for every user, and their basic information.
    The site should be well-styled and attractive.
    The site should use reusable templates for its information. (At a minimum, it should have one Thymeleaf fragment that is used on multiple pages.)
    The site should have a non-whitelabel error handling page that lets the user know, at minimum, the error code and a brief message about what went wrong.
    The site should contain integration testing. At a minimum, there should be tests to ensure basic functionality for the splash page and the sign up page.

## Lab 17
### Feature Tasks
#### Allow users to log in to CodeFellowship and create posts.
    Using the above cheat sheet, add the ability for users to log in to your app.
        Upon logging in, users should be taken to a /myprofile route that displays their information.
    Ensure that your homepage, login, and registration routes are accessible to non-logged in users. All other routes should be limited to logged-in users.
    Ensure that user registration also logs users into your app automatically.
    Add a Post entity to your app.
        A Post has a body and a createdAt timestamp.
        A logged-in user should be able to create a Post, and a post should belong to the user that created it.
            hint: this is a relationship between two pieces of data
    A user’s posts should be visible on their profile page.
    When a user is logged in, the app should display the user’s username on every page (probably in the heading).

## Lab 18
### Feature Tasks
    Ensure that users can’t perform SQL injection or HTML injection with their posts.
    Allow users to follow other users. Following a user means that their posts show up in the logged-in user’s feed, where they can see what all of their followed users have posted recently.
        Ensure there is some way (like a users index page) that a user can discover other users on the service.
        On a user profile page that does NOT belong to the currently logged-in user, display a “Follow” button. When a user clicks that follow button, the logged-in user is now following the viewed-profile-page user.
            note: this will require a self-join on ApplicationUsers.
        A user can visit a url (like /feed) to view all of the posts from the users that they follow.
            Each post should have a link to the user profile of the user who wrote the post.


## Lab 19
### Feature Tasks
    Complete any remaining tasks from CodeFellowship.
    Polish your site’s look and feel.


## How to use it
This is a learning/training lab based application. It's only intended to be run standalone on a developer workstation.

You have to start the Spring Web App Tomcat server, before you can use the application.

And, before that, you'll need a Postgres database created, named fellowship_app. No need to create tables, you just need
the database to exist. Spring will take care of the rest. The database service must be running to do any of that.

You can run this from IntelliJ, by selecting the Spring App class 'CodeFellowshipApplication', and selecting run or debug.

You can also run from the command line using gradle.
./gradlew bootRun
But - first - you might have to hard configure id/pw for db access in the application.properties file.
See application.properties for comments and additional instructions.
Another choice is to export id/pw environment variables.

## API See javadoc
./javadoc/index.html
