#!/bin/bash

# CI service account and policy bindings
gcloud iam service-accounts create push-article-service-ci
gcloud projects add-iam-policy-binding push-to-date \
  --member=serviceAccount:push-article-service-ci@push-to-date.iam.gserviceaccount.com \
  --role=roles/storage.admin
gcloud projects add-iam-policy-binding push-to-date \
  --member=serviceAccount:push-article-service-ci@push-to-date.iam.gserviceaccount.com \
  --role=roles/run.admin
gcloud projects add-iam-policy-binding push-to-date \
  --member=serviceAccount:push-article-service-ci@push-to-date.iam.gserviceaccount.com \
  --role=roles/storage.admin

# App service account and policy bindings
gcloud iam service-accounts create push-article-service-run
gcloud projects add-iam-policy-binding push-to-date \
  --member=serviceAccount:push-article-service-run@push-to-date.iam.gserviceaccount.com \
  --role=roles/cloudsql.client
gcloud projects add-iam-policy-binding push-to-date \
  --member=serviceAccount:push-article-service-run@push-to-date.iam.gserviceaccount.com \
  --role=roles/pubsub.subscriber

# Cloud SQL and user
gcloud sql instances create push-article-service-database \
  --database-version=POSTGRES_13 \
  --cpu=1 \
  --memory=3840MB \
  --region=northamerica-northeast1 \
  --quiet

# Cloud Run create and assign service account
gcloud run create push-article-service-run \
  --image=gcr.io/push-to-date/push-article-service:latest \
  --ingress=all \
  --min-instances=1 \
  --max-instances=1 \
  --timeout=5m \
  --region=northamerica-northeast1 \
  --allow-unauthenticated \
  --add-cloudsql-instances=push-to-date:northamerica-northeast1:push-article-service-database \
  --update-env-vars CLOUD_SQL_CONNECTION_NAME="push-to-date:northamerica-northeast1:push-article-service-database" \
  --update-env-vars DB_USER="postgres" \
  --update-env-vars DB_PASS="example" \
  --update-env-vars DB_NAME="postgres"

# PubSub Subscription
