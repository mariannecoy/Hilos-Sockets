# Taller 04 — Sockets y Threads en Java

[![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Build](https://img.shields.io/badge/build-Makefile-success)](./Makefile)
[![Course](https://img.shields.io/badge/Sistemas%20Distribuidos-PUJ-red)](https://www.javeriana.edu.co/)
[![Status](https://img.shields.io/badge/status-completo-brightgreen)]()

> Taller de la materia **Sistemas Distribuidos** — Pontificia Universidad Javeriana.
> Implementación y comparación de comunicación cliente-servidor con sockets **TCP** y **UDP**, y de programación concurrente con **`extends Thread`**, **`implements Runnable`** y ejecución **secuencial**.

---

## Tabla de contenidos

1. [Autores](#autores)
2. [Descripción](#descripción)
3. [Estructura del repositorio](#estructura-del-repositorio)
4. [Requisitos](#requisitos)
5. [Compilación](#compilación)
6. [Ejecución](#ejecución)
   - [Parte 1 — Sockets](#parte-1--sockets)
   - [Parte 2 — Threads](#parte-2--threads)
7. [Resultados de las pruebas](#resultados-de-las-pruebas)
8. [Documentación](#documentación)

---

## Autores

| Nombre | Rol |
|---|---|
| Daniel Diaz | Desarrollo + documentación |
| Marianne Coy | Desarrollo + documentación |

---

## Descripción

Este repositorio contiene la solución del **Taller 04** de Sistemas Distribuidos, dividido en dos partes:

- **Parte 1 — Sockets TCP/UDP.** Cuatro programas en Java que implementan la comunicación cliente-servidor:
  - `cliUDPsocket` / `serUDPsocket` (puerto **6000**, no orientado a conexión).
  - `cliTCPsocket` / `serTCPsocket` (puerto **6001**, orientado a conexión).
- **Parte 2 — Threads en Java.** Tres programas que resuelven el mismo problema (cajeras procesando los carros de compra de varios clientes) usando:
  - `Main` — ejecución **secuencial**, sin hilos (referencia / *baseline*).
  - `MainThread` — concurrencia mediante **`extends Thread`** (`CajeraThread`).
  - `MainRunnable` — concurrencia mediante **`implements Runnable`** (`Cajera` + `MainRunnable`).

---

## Estructura del repositorio

```text
.
├── TCP_UDP/                 # Parte 1: Sockets
│   ├── cliUDPsocket.java
│   ├── serUDPsocket.java
│   ├── cliTCPsocket.java
│   └── serTCPsocket.java
│
├── THREADS/                 # Parte 2: Threads
│   ├── Cliente.java
│   ├── Cajera.java
│   ├── CajeraThread.java
│   ├── Main.java            # Versión secuencial
│   ├── MainThread.java      # extends Thread
│   └── MainRunnable.java    # implements Runnable
│
├── Makefile                 # Compilación y ejecución
├── Informe.pdf              # Informe técnico del taller
└── README.md                # Este archivo
```

> **Nota.** El repositorio **no contiene** ejecutables, `.class`, `.jar` ni archivos comprimidos: todo se genera localmente al ejecutar `make`.

---

## Requisitos

- **JDK 21** (o superior). Se puede usar OpenJDK.
  ```bash
  sudo apt update && sudo apt install -y openjdk-21-jdk
  java -version
  javac -version
  ```
- **GNU Make** (incluido por defecto en la mayoría de distribuciones Linux).
- Sistema operativo: **Linux** (probado en Ubuntu 24.04).

---

## Compilación

Desde la raíz del proyecto:

```bash
make           # Compila ambas partes (TCP_UDP y THREADS)
make sockets   # Compila solo la parte de sockets
make threads   # Compila solo la parte de threads
make clean     # Elimina los .class generados
make help      # Lista todos los targets disponibles
```

Los `.class` se generan en `build/sockets/` y `build/threads/` (carpetas ignoradas en el repositorio).

---

## Ejecución

### Parte 1 — Sockets

Los sockets requieren **dos terminales** (una para el servidor, otra para el cliente). En ambas terminales se debe estar en la raíz del proyecto.

#### UDP

```bash
# Terminal 1 (servidor)
make ser-udp

# Terminal 2 (cliente)
make cli-udp HOST=localhost
```

Para terminar la sesión, el cliente envía un mensaje que comience con `fin`.

#### TCP

```bash
# Terminal 1 (servidor)
make ser-tcp

# Terminal 2 (cliente)
make cli-tcp HOST=localhost
```

> Si se prueba entre dos máquinas distintas, reemplazar `localhost` por la IP del servidor:
> ```bash
> make cli-tcp HOST=192.168.1.42
> ```

### Parte 2 — Threads

Una sola terminal:

```bash
make run-main       # Versión secuencial (~26 seg)
make run-thread     # extends Thread     (~15 seg)
make run-runnable   # implements Runnable (~15 seg)
```

---

## Resultados de las pruebas

Tiempos medidos en una máquina virtual Linux Ubuntu 24.04 con OpenJDK 21:

| Versión | Mecanismo | Tiempo total | Speed-up vs. secuencial |
|---|---|:---:|:---:|
| `Main` | Secuencial | **26 s** | 1.00x |
| `MainThread` | `extends Thread` | **15 s** | **1.73x** |
| `MainRunnable` | `implements Runnable` | **15 s** | **1.73x** |

**Carros de compra:**
- Cliente 1: `[2, 2, 1, 5, 2, 3]` → 15 s.
- Cliente 2: `[1, 3, 5, 1, 1]` → 11 s.

Como era de esperar, en la versión secuencial el tiempo total es **la suma** (15 + 11 = 26 s), mientras que en las concurrentes es **el máximo** (max(15, 11) = 15 s).

El detalle completo de las pruebas, capturas de pantalla y análisis está en [`Informe.pdf`](./Informe.pdf).

---

## Documentación

- [`Informe.pdf`](./Informe.pdf) — Informe técnico completo (objetivos, marco teórico, diferencias entre TCP/UDP y entre `Thread`/`Runnable`, funcionamiento, experimentos, conclusiones y referencias).
- Cada archivo `.java` incluye su propio **encabezado tipo Javadoc** con autores, propósito y notas de implementación.
