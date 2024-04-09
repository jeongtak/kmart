kubectl delete service kafka-service -n kafka --now
kubectl delete service zookeeper-service -n kafka --now
kubectl delete service kafka -n kafka --now
kubectl delete service zookeeper -n kafka --now

kubectl delete deployment kafka -n kafka --now
kubectl delete deployment kafka-d -n kafka --now
kubectl delete deployment kafka-broker -n kafka --now
kubectl delete deployment zookeeper -n kafka --now