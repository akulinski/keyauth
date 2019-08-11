# EzKeyAuth

Application for generation and verification of serial keys

## Getting Started

Install jdk 12

Clone repo 

Build project using maven (mvn clean install -DskipTest)

Enter docker folder and run ```docker-compose -f docker-compose-devel.yaml up -d```

After project modification rebuild jar with maven and run ```docker-compose -f docker-compose-devel.yaml build``` and 
```docker-compose -f docker-compose-devel.yaml up -d```
### Prerequisites

Maven 

jdk 11

docker

## Deployment

```docker-compose -f docker-compose-devel.yaml up -d```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
