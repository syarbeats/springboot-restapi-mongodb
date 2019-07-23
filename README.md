# Simple Springboot Rest API with Basic Authention and MongoDB as Database Provider 
This project used Swagger-ui to manage API list with its basic authentication hardcoded (admin:admin123) in SwaggerConfig bean. You can set this Authentication on the fly by remove apikey at SecurityConfiguration in SwaggerConfig bean. Don't forget to register user with username admin, with password admin123 firstly in frontend application. This account will be used by swagger-ui authentication (harcodded in SwaggerConfig Bean), you can change swagger-ui authentication as you wish.

## The Stacks:
1. Springboot 2.1.6
2. MongoDB
3. Swagger-ui
4. MockMVC for Integration testing
5. Cucumber & JUnit for High Level's Like Integration Testing
6. Cucumber for Frontend High Level Testing (Frontend: https://github.com/syarbeats/react-frontend-login-register.git)
7. Chrome Webdriver

### Workflow

<img width="512" alt="flow" src="https://user-images.githubusercontent.com/18225438/61030681-1c0f0f80-a3e8-11e9-9322-e65e298b26d7.PNG">


### Swagger-ui Screenshoot

<img width="685" alt="Swagger1" src="https://user-images.githubusercontent.com/18225438/61031255-4f05d300-a3e9-11e9-8e19-3340ab44e581.PNG">
