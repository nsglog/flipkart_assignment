#PROJECT-SETUP
 
## Requirements : Java (version 17 or above), MySQL .
 
Steps
1) Open application.properties file.
 
2) Update the following fields
```groovy
spring.datasource.username = your_mysql_username            
spring.datasource.password = your_mysql_password.
```
 
3) Now open a terminal open the cloned file and go to the folder which contains schema.sql file (this file basically contains mysql dump file which will create the schema).
 
4) Run the following command : 
```bash
> create database news_feed;
> mysql -u your_mysql_username -p news_feed < schema.sql;
```
5) Now run springboot application using command: `./mvnw spring-boot:run`
 
 
# news-feed-cli
A console based news feed which can simulate a social network

#COMMANDS :
 
      //command-name: implementation of command . eg : create-post is command name and it is implemented like this create-post --text 'enter post text here'.
 
       * up-vote-post: up-vote-post --id 'post_id'
       * create-post: create-post --text 'post_content' 
       * down-vote-post: down-vote-post --id 'post_id'
       * comment-on-post: comment-on-post --id 'post_id' --text 'comment_text'
       * up-vote-comment: up-vote-comment --id 'comment_id'
       * login: login --u 'username' --p 'password' 
       * signup: signup --u 'username' --e 'email' --p 'password'
       * follow-user: follow-user --u 'username'
       * reply-on-comment: reply-on-comment --id 'comment_id' --text 'reply_text'
       * show-news-feed: show-news-feed --sortOrder 'sort order'    //sort orders :'followed_users', 'vote_score', 'comment_score', 'timestamp'
       * logout: logout
       * show-comments-on-post: show-comments-on-post --id 'postId'
       * show-replies-on-comment: show-replies-on-comment --id 'commentId'
       * down-vote-comment: down-vote-comment --id 'comment_id'
 
       Some of the commands might not be available at all the time . for eg :
          All commands which results in changing the properties of post like comment-on-post, up-vote-post, down-vote-post are only available
          once you have requested to see all the posts by using command show-news-feed --sortOrder 'any sort order'.
 
          Similarly, all commands which results in changing the properties of a comment like up-vote-comment, down-vote-comment, reply-on-comment 
          and show-replies-on-comment are only available once you have requested to see all the comments which are created against a post .
 
          login and signup are only available commands when the application starts.
 
          logout command is only available once a user has logged in.
 
          /*   
            To check the list of available commands at any moment, enter command 'help'. This will list all the commands available to use 
            with conditions. Some of the commands will be marked with '*' while some are not . So those commands which are currently 
            available to use are 'NOT' marked with '*'. 
          */
 
