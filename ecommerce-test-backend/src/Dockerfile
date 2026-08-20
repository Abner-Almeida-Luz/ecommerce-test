# Stage 1: build — usa uma imagem com Maven completo
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia só o pom.xml primeiro — camada de cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# ↑ Se você só mudar código Java (não o pom.xml), o Docker reaproveita
#   essa camada e não baixa as dependências de novo — build muito mais rápido

COPY src ./src
RUN mvn clean package -DskipTests
# ↑ Testes já rodaram no CI (GitHub Actions) — não precisa rodar de novo no build da imagem

# Stage 2: runtime — imagem final, SEM Maven, SEM código fonte
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# ↑ Só o .jar final é copiado — a imagem final não carrega o Maven nem
#   o código fonte, só o artefato compilado. Isso reduz o tamanho da
#   imagem de ~600MB (com Maven+JDK completo) para ~200MB (JRE + jar)

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
    CMD wget --spider -q http://localhost:8080/actuator/health || exit 1