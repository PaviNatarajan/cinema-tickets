# Ticket Service Implementation

## Overview
This project implements a Ticket Service for purchasing cinema tickets. It applies business rules for ticket validation, pricing, and seat reservation.

## Features
- Supports Adult, Child, and Infant ticket types
- Validates purchase rules
- Calculates total price
- Calculates seat reservations
- Rejects invalid purchase requests

## Business Rules
- Maximum 25 tickets per purchase
- Infant tickets cost £0 and do not receive a seat
- Child and Infant tickets require at least one Adult ticket
- Each Infant must be accompanied by an Adult
- Pricing:
    - Adult: £25
    - Child: £15
    - Infant: £0

## Tech Stack
- Java
- JUnit 5 for testing

## Assumptions
- All account IDs greater than 0 are valid
- Payment and seat reservation services always succeed
- External services are trusted and error-free

## How to Run Tests
Run the following command:
mvn test
