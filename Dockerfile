FROM gradle:jdk17-jammy
LABEL maintainer="eduardo_fsilva"
WORKDIR /app
COPY . .
RUN gradle build -x test
EXPOSE 8080
CMD ["gradle", "bootRun"]