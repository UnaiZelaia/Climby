# Stage 1: Build stage
FROM golang:1.22.2-alpine AS builder

# Set necessary environment variables
ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

# Create a directory for the app
WORKDIR /app

# Copy and download dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy the source code
COPY . .

# Build the app
RUN go build -o climby .

# Build the migrations binary
RUN go build -o migrations/migrations migrations/migrations.go

# Stage 2: Production stage
FROM alpine:latest

# Set the working directory
WORKDIR /root/

# Copy only the built binary from the builder stage
COPY --from=builder /app/climby .
COPY --from=builder /app/migrations/migrations ./migrations

# Copy the entrypoint script and make it executable
COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

# Copy the .env file
COPY .env .
COPY --from=builder /app/certs ./certs

# Set the entry point
ENTRYPOINT ["/root/entrypoint.sh"]

