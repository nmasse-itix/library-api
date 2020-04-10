# Library API

```
oc new-project library-api
oc create secret generic 3scale-toolbox -n library-api --from-file="$HOME/.3scalerc.yaml"
oc new-app -n library-api --template=jenkins-ephemeral --name=jenkins -p MEMORY_LIMIT=2Gi
oc set env -n library-api dc/jenkins JENKINS_OPTS=--sessionTimeout=86400
oc import-image openjdk-8-rhel8 --from=registry.redhat.io/openjdk/openjdk-8-rhel8 --confirm -n openshift --reference-policy=local
oc new-app -n library-api -i openjdk-8-rhel8 https://github.com/nmasse-itix/library-api.git --name=library-api
oc expose -n library-api svc/library-api --hostname="library-api.apps.ocp4.itix.fr"
```
