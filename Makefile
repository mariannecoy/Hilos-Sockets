# =============================================================================
#  Universidad: Pontificia Universidad Javeriana
#  Materia:     Sistemas Distribuidos
#  Taller:      Sockets y Threads en Java
#  Autores:     Daniel Diaz y Marianne Coy
#
#  Makefile para compilar y ejecutar las dos partes del taller:
#    1) Sockets TCP/UDP (carpeta TCP_UDP/)
#    2) Threads en Java (carpeta THREADS/)
#
#  Uso rápido:
#    make            -> compila ambas partes
#    make run-main   -> ejecuta la versión secuencial de threads
#    make run-thread -> ejecuta la versión con extends Thread
#    make run-runnable -> ejecuta la versión con Runnable
#    make ser-udp / cli-udp HOST=localhost
#    make ser-tcp / cli-tcp HOST=localhost
#    make clean      -> elimina los .class generados
# =============================================================================

JAVAC      := javac
JAVA       := java

SOCKETS_SRC_DIR := TCP_UDP
THREADS_SRC_DIR := THREADS

BUILD_DIR        := build
SOCKETS_OUT_DIR  := $(BUILD_DIR)/sockets
THREADS_OUT_DIR  := $(BUILD_DIR)/threads

SOCKETS_SRCS := $(wildcard $(SOCKETS_SRC_DIR)/*.java)
THREADS_SRCS := $(wildcard $(THREADS_SRC_DIR)/*.java)

# Host por defecto para los clientes de sockets (sobreescribible: HOST=otro)
HOST ?= localhost

.PHONY: all sockets threads run-main run-thread run-runnable \
        ser-udp cli-udp ser-tcp cli-tcp clean help

# -----------------------------------------------------------------------------
# Compilación
# -----------------------------------------------------------------------------
all: sockets threads

sockets: $(SOCKETS_OUT_DIR)/.compiled
$(SOCKETS_OUT_DIR)/.compiled: $(SOCKETS_SRCS)
	@mkdir -p $(SOCKETS_OUT_DIR)
	$(JAVAC) -d $(SOCKETS_OUT_DIR) $(SOCKETS_SRCS)
	@touch $@

threads: $(THREADS_OUT_DIR)/.compiled
$(THREADS_OUT_DIR)/.compiled: $(THREADS_SRCS)
	@mkdir -p $(THREADS_OUT_DIR)
	$(JAVAC) -d $(THREADS_OUT_DIR) $(THREADS_SRCS)
	@touch $@

# -----------------------------------------------------------------------------
# Ejecución - Threads
# -----------------------------------------------------------------------------
run-main: threads
	@echo "=== Ejecutando Main (SECUENCIAL) ==="
	$(JAVA) -cp $(THREADS_OUT_DIR) tallerThreads.Main

run-thread: threads
	@echo "=== Ejecutando MainThread (extends Thread) ==="
	$(JAVA) -cp $(THREADS_OUT_DIR) tallerThreads.MainThread

run-runnable: threads
	@echo "=== Ejecutando MainRunnable (implements Runnable) ==="
	$(JAVA) -cp $(THREADS_OUT_DIR) tallerThreads.MainRunnable

# -----------------------------------------------------------------------------
# Ejecución - Sockets
#
#   IMPORTANTE: el servidor debe iniciarse PRIMERO en una terminal,
#   y el cliente en otra terminal distinta.
# -----------------------------------------------------------------------------
ser-udp: sockets
	$(JAVA) -cp $(SOCKETS_OUT_DIR) serUDPsocket

cli-udp: sockets
	$(JAVA) -cp $(SOCKETS_OUT_DIR) cliUDPsocket $(HOST)

ser-tcp: sockets
	$(JAVA) -cp $(SOCKETS_OUT_DIR) serTCPsocket

cli-tcp: sockets
	$(JAVA) -cp $(SOCKETS_OUT_DIR) cliTCPsocket $(HOST)

# -----------------------------------------------------------------------------
# Limpieza
# -----------------------------------------------------------------------------
clean:
	rm -rf $(BUILD_DIR)
	@echo "Limpieza completa."

help:
	@echo "Targets disponibles:"
	@echo "  make                -> compila sockets + threads"
	@echo "  make run-main       -> ejecuta Main (secuencial)"
	@echo "  make run-thread     -> ejecuta MainThread (extends Thread)"
	@echo "  make run-runnable   -> ejecuta MainRunnable (Runnable)"
	@echo "  make ser-udp        -> arranca el servidor UDP"
	@echo "  make cli-udp [HOST=...] -> arranca el cliente UDP"
	@echo "  make ser-tcp        -> arranca el servidor TCP"
	@echo "  make cli-tcp [HOST=...] -> arranca el cliente TCP"
	@echo "  make clean          -> elimina los .class generados"
