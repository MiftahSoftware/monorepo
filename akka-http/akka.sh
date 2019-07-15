echo "Checkout Directory"
cd akka-http

echo "code compile"
sbt clean compile

echo "code test"
sbt clean test

echo "generate artifact"
sbt clean assembly

echo "build docker file"
docker build -t akka-http:latest -f DockerFile .

echo "push docker image"
docker tag akka-http:latest azmathasan92/akka-http-service:1.0
docker login --username $USERNAME --password $PASSWORD
docker push azmathasan92/akka-http-service:1.0
