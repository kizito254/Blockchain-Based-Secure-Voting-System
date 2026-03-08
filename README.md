# Blockchain-Based Secure Voting System

This repository contains a starter implementation for a **tamper-resistant online voting system** using:

- **Java 17**
- **Spring Boot**
- A pluggable blockchain gateway (ready to wire to **Hyperledger Fabric** or **Ethereum API**)

## Current Implementation

The `backend` service exposes core APIs for:

1. **Voter authentication** (`POST /api/auth/login`)
2. **Secure vote storage** (`POST /api/votes`)
3. **Transparent vote counting** (`GET /api/votes/results?electionId=...`)

For local development, votes are persisted via an in-memory blockchain adapter (`InMemoryBlockchainGateway`) that emulates on-chain writes and tally reads.

## Project Structure

```text
backend/
  src/main/java/com/voting/
    auth/         # login endpoint
    vote/         # voting APIs + service
    blockchain/   # gateway abstraction + in-memory implementation
    common/       # global errors
```

## Run

```bash
cd backend
mvn spring-boot:run
```

## Sample API Usage

Authenticate a voter:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"voterId":"voter-1","nationalId":"123456789","otp":"123456"}'
```

Cast a vote:

```bash
curl -X POST http://localhost:8080/api/votes \
  -H "Content-Type: application/json" \
  -d '{"voterId":"voter-1","electionId":"election-2026","candidateId":"candidate-a","accessToken":"token"}'
```

Get transparent results:

```bash
curl "http://localhost:8080/api/votes/results?electionId=election-2026"
```

## Next Steps

- Replace `InMemoryBlockchainGateway` with:
  - Hyperledger Fabric chaincode integration, or
  - Ethereum/Web3 client integration.
- Add cryptographic signatures for wallet-backed voter identities.
- Add a React or Angular frontend for voter and admin dashboards.
- Add role-based admin APIs for election creation and audits.
