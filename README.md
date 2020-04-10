# Library API

## Setup

Create a project and deploy the Library API Backend.

```sh
oc new-project library-api
oc import-image -n openshift openjdk-8-rhel8 --from=registry.redhat.io/openjdk/openjdk-8-rhel8 --confirm --reference-policy=local
oc new-app -n library-api -i openjdk-8-rhel8 https://github.com/nmasse-itix/library-api.git --name=library-api
oc expose -n library-api svc/library-api --hostname="library-api.apps.ocp4.itix.fr"
```

Deploy a Jenkins master.

```sh
oc new-app -n library-api --template=jenkins-ephemeral --name=jenkins -p MEMORY_LIMIT=2Gi
oc set env -n library-api dc/jenkins JENKINS_OPTS=--sessionTimeout=86400
```

Create a secret containing your 3scale toolbox remotes.

```sh
oc create -n library-api secret generic 3scale-toolbox --from-file="$HOME/.3scalerc.yaml"
```

Add a new Build Config to run the Jenkins pipeline.

```sh
oc new-build -n library-api --strategy=pipeline --name=library-pipeline https://github.com/nmasse-itix/library-api.git -e PRIVATE_BASE_URL=http://library-api.apps.ocp4.itix.fr -e NAMESPACE=library-api -e TARGET_INSTANCE=3scale-saas -e SECRET_NAME=3scale-toolbox -e OIDC_ISSUER_ENDPOINT=https://zync:[REDACTED]@sso.apps.ocp4.itix.fr/auth/realms/3scale-saas -e DISABLE_TLS_VALIDATION=yes -e MOCK_SERVER=https://microcks.apps.ocp4.itix.fr -e MOCK_URL=/rest/Library/0.9.0
```

## Reset

Remove the Jenkins pipeline:

```sh
oc delete -n library-api bc/library-pipeline
```

Remove the 3scale services:

```sh
ansible-playbook cleanup/cleanup.yml -e admin_portal_hostname=[TENANT]-admin.3scale.net -e threescale_token=[REDACTED]
```

Go to the [Apicurio Studio](https://apicurio-studio.app.itix.fr/apis) and remove the **Library** service.

Go to the Microcks console and remove the **Library** service.

Set the OpenAPI Specification file back to version 0.9:

```sh
mv api-contracts/openapi-0.9.json openapi.json
git add openapi.json
git commit -m 'reset the demo'
git push
```
