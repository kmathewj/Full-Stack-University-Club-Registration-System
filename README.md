# Full-Stack-University-Club-Registration-System

## Overview
A full-stack web application designed to streamline university club registration and management. This system enables students to apply for clubs, and administrators to review and manage applications efficiently.

---

## Features
1. **User Authentication**
    - Student and admin login
    - secure session handling
2. **Club Registration**
    - Students can apply to join clubs
    - Track application status
3. **Club Management**
    - Admins can approve/reject applications
    - Manage club memberships
4. **Data Persistence**
    - MySQL database integration
    - Structured schema for students, admins, applications, and clubs
5. **Cloud Deployment**
    - Hosted on Google Cloud
    - Designed for scalability and distributed architecture

---

## Architecture
This project was designed using the Microservices Architecture:
- Frontend Service (UI + authentication + orchestration)
- Club Registration Service (handles applications)
- Club Management Service (handles approvals & memberships)
