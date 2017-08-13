#!/bin/bash

# Deploy all "/spring-specification/spring-specification-doc/target/generated-docs/*" files to "gh-pages" branches.
# Require GITHUB_USERNAME & GITHUB_PASSWORD environment variables for GitHub access.

REPO=https://${GITHUB_USERNAME}:${GITHUB_PASSWORD}@github.com/pinguet62/spring-specification.git

echo "Cloning gh-pages branch..."
git clone --branch=gh-pages $REPO gh-pages
cd gh-pages

echo "Cleaning existing docs..."
rm -rf *
echo "Copying new docs..."
cp -r ../target/generated-docs/* .

git add --all .
git commit --message "Update"
git push --quiet $REPO

cd ..
rm -rf gh-pages
