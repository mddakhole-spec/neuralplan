<div align="center">

# 🧠 NeuralPlan
### AI-Powered Personalized Study Planner

[![Live Demo](https://img.shields.io/badge/Live%20Demo-neuralplan.onrender.com-blue?style=for-the-badge)](https://neuralplan.onrender.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Gemini AI](https://img.shields.io/badge/Gemini%20AI-2.5%20Flash-orange?style=for-the-badge&logo=google)](https://ai.google.dev/)
[![Docker](https://img.shields.io/badge/Docker-Deployed-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Java](https://img.shields.io/badge/Java-17-red?style=for-the-badge&logo=java)](https://www.java.com/)

*Enter your subjects, exam date, and weak topics — NeuralPlan builds you a day-by-day study schedule powered by Gemini AI.*

</div>

---

## ✨ Features

- 🤖 **AI-Generated Study Plans** — Gemini AI creates personalized day-by-day schedules based on your subjects, exam date, and weak topics
- 📅 **Date-Aware Planning** — Plans are generated relative to today's date for accurate scheduling
- 💾 **Plan Persistence** — All generated plans are saved to database and displayed on the dashboard
- 🎨 **Premium Dark UI** — Clean, Vercel-inspired dark interface with markdown rendering
- ⚡ **Error Handling** — Graceful handling of API rate limits and failures
- 🚀 **Cloud Deployed** — Live and accessible via public URL

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.5 |
| AI Integration | Gemini AI API (gemini-2.5-flash) via WebFlux WebClient |
| Database | H2 (dev) / PostgreSQL (prod) |
| ORM | Spring Data JPA / Hibernate |
| Frontend | Thymeleaf, HTML, CSS, Marked.js |
| Containerization | Docker |
| Deployment | Render (Cloud Platform) |
| Build Tool | Maven |

---

## 🚀 Live Demo

👉 **[https://neuralplan.onrender.com](https://neuralplan.onrender.com)**

> Note: Hosted on Render free tier — app may take ~30 seconds to wake up on first visit.

---

## 📁 Project Structure
neuralplan/

├── src/main/java/com/neuralplan/neuralplan/

│   ├── controller/

│   │   └── StudyPlanController.java    # HTTP request handling

│   ├── model/

│   │   └── StudyPlan.java              # Entity class

│   ├── repository/

│   │   └── StudyPlanRepository.java    # Database operations

│   ├── service/

│   │   ├── StudyPlanService.java       # Business logic

│   │   └── GeminiService.java          # Gemini AI integration

│   └── NeuralplanApplication.java

├── src/main/resources/

│   ├── templates/

│   │   └── index.html                  # Thymeleaf frontend

│   └── application.properties

└── Dockerfile

---

## ⚙️ Run Locally

**Prerequisites:** Java 17+, Maven

```bash
# Clone the repo
git clone https://github.com/mddakhole-spec/neuralplan.git
cd neuralplan

# Add your Gemini API key in application.properties
gemini.api.key=YOUR_API_KEY
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent

# Run
./mvnw spring-boot:run
```

App runs at `http://localhost:8080`

---

## 🐳 Run with Docker

```bash
docker build -t neuralplan .
docker run -p 8080:8080 \
  -e GEMINI_API_KEY=your_key \
  -e GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent \
  neuralplan
```

---

## 🏗️ Architecture
Browser → Thymeleaf (Frontend)

↓

StudyPlanController (HTTP Layer)

↓

StudyPlanService (Business Logic)

↙        ↘

GeminiService    StudyPlanRepository

(Gemini AI API)  (H2/PostgreSQL DB)

---

## 👩‍💻 Author

**Mugdha Dakhole**
- GitHub: [@mddakhole-spec](https://github.com/mddakhole-spec)
- LinkedIn: [linkedin.com/in/01mugdha-dakhole](https://linkedin.com/in/01mugdha-dakhole)
- Live Project: [neuralplan.onrender.com](https://neuralplan.onrender.com)

---

<div align="center">
Built with ☕ Java and 🤖 Gemini AI
</div>
