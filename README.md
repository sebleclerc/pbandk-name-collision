## Test Repository to validate nested message name clash

This repo already contains a Kotlin class but here is how to recreate:
`protoc --kotlin_out=<path to repo>test-nested/out -I. proto/*.proto  --plugin=protoc-gen-kotlin=<oath to pbandk>/protoc-gen-kotlin/protoc-gen-kotlin-jvm/build/install/protoc-gen-kotlin/bin/protoc-gen-kotlin`
