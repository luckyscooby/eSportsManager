<div align="center">
  <img src="https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk" alt="Java 21 Badge">
  <img src="https://img.shields.io/badge/JavaFX-17-orange?style=for-the-badge&logo=java" alt="JavaFX 17 Badge">
  <img src="https://img.shields.io/badge/Maven-3.9-red?style=for-the-badge&logo=apachemaven" alt="Maven Badge">
  <img src="https://img.shields.io/badge/MariaDB-10.x-003545?style=for-the-badge&logo=mariadb" alt="MariaDB Badge">
  <img src="https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge" alt="License Badge">
</div>

<br>

<h1 align="center">🎮 eSports Manager 🏆</h1>

<p align="center">
  <strong>Sistema de desktop completo para gerenciamento de equipes, jogadores e projetos no cenário de eSports</strong>
</p>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre</a> •
  <a href="#-funcionalidades">Funcionalidades</a> •
  <a href="#-instalação-rápida">Instalação</a> •
  <a href="#%EF%B8%8F-desenvolvimento">Desenvolvimento</a> •
  <a href="#-contribuição">Contribuição</a> •
  <a href="#-licença">Licença</a>
</p>

---

## 📋 Sobre o Projeto

O **eSports Manager** é uma aplicação desktop desenvolvida para simplificar e centralizar a gestão de organizações de eSports. Projetado para administradores, técnicos e jogadores, oferece uma solução intuitiva para o controle completo das operações diárias de uma equipe profissional.

### 🎯 Objetivos

- Centralizar informações de jogadores, equipes e projetos
- Simplificar a gestão administrativa de organizações eSports
- Fornecer relatórios e estatísticas relevantes
- Facilitar a comunicação entre membros da organização

### 🏗️ Arquitetura

```
eSports Manager
├── Frontend (JavaFX)
├── Backend (Java)
├── Database (MariaDB)
└── Build Tool (Maven)
```

---

## ✨ Funcionalidades

### 📊 Dashboard Inteligente
- Visão geral das estatísticas da organização
- Próximos eventos e marcos importantes
- Indicadores de performance das equipes

### 👥 Gestão de Usuários
- Sistema completo de CRUD para todos os membros
- Controle de acesso por cargo (Administrador, Técnico, Jogador)
- Gerenciamento de perfis e permissões

### 🏆 Gerenciamento de Equipes
- Criação e administração de equipes
- Suporte para múltiplos jogos (LoL, CS2, Valorant, outros)
- Controle de membros por equipe

### 📅 Gestão de Projetos
- Organização de campeonatos, eventos e treinamentos
- Controle de status e prazos
- Designação de gerentes responsáveis

---

## 🚀 Instalação Rápida

### Pré-requisitos Mínimos
- **Java Runtime Environment (JRE) 21+**
- Sistema operacional: Windows 10+, macOS 10.14+, ou Linux

### Download e Execução

1. **Baixe a versão mais recente:**
   - Acesse a seção [Releases](../../releases) deste repositório
   - Baixe o arquivo `eSportsManager-1.0.jar`

2. **Execute a aplicação:**
   ```bash
   java -jar eSportsManager-1.0.jar
   ```

### Instalação do Java (se necessário)

#### Windows/macOS
- Baixe o OpenJDK em [adoptium.net](https://adoptium.net/)
- Execute o instalador e marque "Adicionar ao PATH"

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-21-jre
```

#### Linux (Fedora/RHEL)
```bash
sudo dnf install java-21-openjdk
```

---

## 🛠️ Desenvolvimento

### Pré-requisitos para Desenvolvedores

| Ferramenta | Versão Mínima | Download |
|------------|---------------|----------|
| Git | Latest | [git-scm.com](https://git-scm.com/downloads) |
| JDK | 21+ | [adoptium.net](https://adoptium.net/) |
| Maven | 3.9+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| MariaDB | 10.x | [mariadb.org](https://mariadb.org/download/) |

### Configuração do Ambiente

#### 1. Clone o repositório
```bash
git clone https://github.com/luckyscooby/eSportsManager.git
cd eSportsManager
```

#### 2. Configure o MariaDB

**Instalar e iniciar o serviço:**
```bash
# Ubuntu/Debian
sudo apt install mariadb-server
sudo systemctl start mariadb
sudo systemctl enable mariadb

# Fedora/RHEL
sudo dnf install mariadb-server
sudo systemctl start mariadb
sudo systemctl enable mariadb

# Configuração de segurança
sudo mysql_secure_installation
```

**Criar o banco de dados:**
```bash
# Conectar ao MariaDB
sudo mysql -u root -p

# Executar no prompt do MariaDB
CREATE DATABASE esports_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3. Executar script de inicialização
```sql
USE esports_manager;

-- Tabela de Usuários
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    cargo ENUM('Administrador', 'Técnico', 'Jogador') NOT NULL,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Equipes
CREATE TABLE Equipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_equipe VARCHAR(255) NOT NULL,
    jogo ENUM('LoL', 'CS2', 'Valorant', 'Outro') NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Projetos
CREATE TABLE Projetos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_projeto VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_inicio DATE,
    data_termino_prevista DATE,
    status ENUM('Planejado', 'Em Andamento', 'Concluído', 'Cancelado') NOT NULL,
    id_gerente INT,
    FOREIGN KEY (id_gerente) REFERENCES Usuarios(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tabela de Membros das Equipes
CREATE TABLE MembrosEquipe (
    id_usuario INT NOT NULL,
    id_equipe INT NOT NULL,
    PRIMARY KEY (id_usuario, id_equipe),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_equipe) REFERENCES Equipes(id) ON DELETE CASCADE
) ENGINE=InnoDB;
```

#### 4. Configurar conexão com banco
Edite `src/main/java/com/anima/esportsmanager/util/ConexaoDB.java` e atualize a senha do MariaDB.

#### 5. Compilar e executar
```bash
# Limpar, compilar e executar em modo de desenvolvimento
mvn clean javafx:run
```

### Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   └── com/anima/esportsmanager/
│   │       ├── controller/     # Controladores JavaFX
│   │       ├── dao/            # Objetos de Acesso a Dados
│   │       ├── model/          # Modelos de dados
│   │       ├── util/           # Utilitários (BD, etc.)
│   │       ├── ESportsManager.java # Classe de Aplicação JavaFX
│   │       └── Launcher.java       # Classe de Lançamento (Principal)
│   └── resources/
│       └── view/               # Arquivos FXML das interfaces
└── test/                       # (Opcional) Testes unitários
```

---

## 🧪 Testes

```bash
# Executar todos os testes (se existirem)
mvn test
```

---

## 📦 Build e Deploy

### Gerar executável
```bash
mvn clean package
```

O arquivo JAR final e distribuível será gerado em `target/eSportsManager-1.0.jar`. O arquivo `original-eSportsManager-1.0.jar` é um artefato do processo de build e pode ser ignorado.

---

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Siga estes passos:

1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. **Abra** um Pull Request

### Padrões de Código
- Utilize Java Code Conventions
- Escreva testes para novas funcionalidades
- Mantenha a documentação atualizada

---

## 🐛 Reportar Bugs

Encontrou um bug? Abra uma [issue](../../issues/new) com:

- Descrição clara do problema
- Passos para reproduzir
- Comportamento esperado vs. atual
- Screenshots (se aplicável)
- Informações do sistema

---

## 📝 Changelog

### Versão 1.0 (Em desenvolvimento)
- ✅ Sistema básico de CRUD para usuários
- ✅ Gerenciamento de equipes
- ✅ Dashboard informativo
- 🔄 Sistema de autenticação
- 📋 Relatórios e estatísticas

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
