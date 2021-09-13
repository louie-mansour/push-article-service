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
gcloud sql instances create push-article-service-db \
  --database-version=POSTGRES_13 \
  --cpu=1 \
  --memory=3840MB \
  --region=northamerica-northeast1 \
  --quiet
gcloud sql users set-password postgres \
--instance=push-article-service-db \
--prompt-for-password

# Cloud Run create and assign service account
gcloud run deploy push-article-service-run \
  --image=gcr.io/push-to-date/push-article-run:latest \
  --ingress=all \
  --min-instances=1 \
  --max-instances=1 \
  --timeout=5m \
  --region=northamerica-northeast1 \
  --no-allow-unauthenticated \
  --service-account=push-article-service-run@push-to-date.iam.gserviceaccount.com

# PubSub Subscription
