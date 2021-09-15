FROM openjdk:11 as builder
COPY . .
#ARG PROFILES="prod"
RUN SPRING_PROFILES_ACTIVE=prod ./gradlew build -x test --debug

FROM openjdk:11 as release
COPY --from=builder ./build/libs/articleservice.jar app.jar
#ARG PROFILES="prod"
#ENV PROFILES=$PROFILES
ENTRYPOINT ["java", "-Dspring.profiles.active=prod"]
CMD ["-jar", "app.jar"]