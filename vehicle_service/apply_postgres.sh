kubectl apply -f k8s/postgres-credentials.yaml -n ns-9
kubectl apply -f k8s/postgres-configmap.yaml -n ns-9
kubectl apply -f k8s/postgres-deployment.yml -n ns-9