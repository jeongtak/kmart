curl -i -s -X POST http://localhost:8001/services --data name=main --data url=http://host.docker.internal:8081/
curl -i -s -X POST http://localhost:8001/services --data name=getProductList --data url=http://host.docker.internal:8081/getProductList
curl -i -s -X POST http://localhost:8001/services --data name=reqProductPayment --data url=http://host.docker.internal:8082/reqProductPayment

curl -i -X POST http://localhost:8001/services/main/routes  --data methods=GET --data paths[]=/ --data name=main
curl -i -X POST http://localhost:8001/services/getProductList/routes  --data methods=GET  --data paths[]=/getProductList --data name=getProductList
curl -i -X POST http://localhost:8001/services/reqProductPayment/routes  --data methods=GET  --data paths[]=/reqProductPayment  --data name=reqProductPayment
