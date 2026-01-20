#!/bin/bash
# EC2 Setup Script for K3s and GitHub Self-Hosted Runner
# Run this on an Ubuntu 22.04 EC2 instance

set -e

echo "========================================="
echo "  Setting up K3s and GitHub Runner"
echo "========================================="

# Update system
echo "[1/4] Updating system..."
sudo apt-get update && sudo apt-get upgrade -y

# Install required packages
echo "[2/4] Installing packages..."
sudo apt-get install -y curl wget git jq

# Install K3s
echo "[3/4] Installing K3s..."
curl -sfL https://get.k3s.io | sh -

# Wait for K3s
sleep 30

# Configure kubectl
echo "[4/4] Configuring kubectl..."
mkdir -p ~/.kube
sudo cp /etc/rancher/k3s/k3s.yaml ~/.kube/config
sudo chown $(id -u):$(id -g) ~/.kube/config
export KUBECONFIG=~/.kube/config
echo 'export KUBECONFIG=~/.kube/config' >> ~/.bashrc

# Verify
kubectl get nodes

echo "========================================="
echo "  K3s Setup Complete!"
echo "========================================="
echo ""
echo "Next: Set up GitHub Runner"
echo "1. Go to: GitHub Repo -> Settings -> Actions -> Runners -> New self-hosted runner"
echo "2. Follow the instructions to download and configure the runner"
echo ""
