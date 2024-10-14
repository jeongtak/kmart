kubectl delete service zookeeper-service --now
kubectl delete service kafka --now
kubectl delete service zookeeper --now
kubectl delete service product-db --now

kubectl delete deployment kafka --now
kubectl delete deployment kafka-broker --now
kubectl delete deployment zookeeper --now
kubectl delete deployment postgresql --now

kubectl delete ingress kmart-ingress --now