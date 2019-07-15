echo "code compile"
mvn clean compile

echo "code test"
mvn clean test

echo "generate artifact"
mvn clean package

echo "build docker file"
docker build -t lagom-svc:latest -f Dockerfile .

echo "push docker image"
docker tag lagom-svc:latest azmathasan92/lagom-service:1.0
docker login --username $USERNAME --password $PASSWORD
docker push azmathasan92/lagom-service:1.0
