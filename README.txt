<div align="center">

# 🏪 QL-CuaHangTienLoi
### Quản Lý Cửa Hàng Tiện Lợi — Convenience Store Management System

*Đồ án môn Phát triển phần mềm hướng đối tượng (OOP Software Development)*

![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-InnoDB-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-PreparedStatement-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Status](https://img.shields.io/badge/Status-Academic%20Project-success?style=for-the-badge)
![License](https://img.shields.io/badge/License-Educational-lightgrey?style=for-the-badge)

</div>

---

## 📖 Overview

Desktop app (Java + JDBC + MySQL) managing sales for a convenience store. Multi-user, role-based (Admin / Staff), handles imports, sales invoices, inventory, reports.

## ✨ Features

| Module | Desc |
|---|---|
| 🧾 Product mgmt | product types, units, suppliers |
| 📥 Stock-in | import receipts, line items, auto total, stock update |
| 💰 Sales | invoices, line items, auto total, stock deduction |
| 👤 Customer/Staff | CRUD for customers & employees |
| 🔐 Accounts | login + role-based access (Admin/Staff) |
| 📊 Reports | daily revenue, stock levels, best-sellers |
| ⚙️ Concurrency | data consistency when multiple sellers touch same product |

## 🛠️ Tech Stack

<div align="left">

![Java](https://img.shields.io/badge/Java-JDK%208+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-InnoDB%20engine-4479A1?style=flat-square&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Connector%2FJ-F80000?style=flat-square&logo=oracle&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-orange?style=flat-square&logo=java&logoColor=white)

</div>

- **Lang:** Java (JDK 8+)
- **DB access:** JDBC — `PreparedStatement`, connection pooling
- **DB:** MySQL, InnoDB engine
- **Driver:** `mysql-connector.jar` (in `lib/`)
- **GUI:** Java desktop (Swing)
- **IDE options:** Eclipse, IntelliJ IDEA, VS Code + Java extension pack

## 📂 Repo Structure

```
QL-CuaHangTienLoi/
├── DevNotes/     # dev notes / progress logs
├── SQL/          # qlcuahangtienloi.sql — schema + seed data
├── data/         # sample/runtime data
├── database/     # DB-related assets
├── docs/         # docs
├── lib/          # mysql-connector.jar (JDBC driver)
├── src/          # Java source, entry: src/main/main.java
└── README.txt    # original readme
```

## 🚀 Getting Started

### Prereqs
- MySQL (stable release) installed
- Java JDK 8+
- IDE: Eclipse / IntelliJ / VS Code + Java plugin

### Setup

```bash
# 1. clone
git clone https://github.com/DZT711/QL-CuaHangTienLoi.git
```

2. **Create DB** — new MySQL DB (e.g. `qlcuahang`), then import `SQL/qlcuahangtienloi.sql`.
3. **Configure conn** — set URL / username / password in Java JDBC config file.
4. **Add driver** — put `lib/mysql-connector.jar` on project classpath.
5. **Run** — open in IDE, set run config main class → `src/main/main.java`.

### Login (test accounts)

| Role | Username | Password |
|---|---|---|
| Admin | `an` | `an` |
| Staff | `anh` | `anh` |

## 🧠 What I Learned

- OOP design applied to real inventory/sales domain (entities: product, unit, supplier, receipt, invoice, customer, staff, account)
- JDBC fundamentals: `Connection`, `PreparedStatement`, connection pooling, avoiding SQL injection
- MySQL schema design w/ InnoDB — foreign keys, transactions
- Handling concurrent stock updates → data consistency (race conditions on shared inventory)
- Role-based auth (Admin vs Staff) at app layer
- Report generation (aggregation queries — revenue/day, stock, best-sellers)
- Java desktop GUI dev + IDE/classpath/driver config across Eclipse/IntelliJ/VS Code

## 👥 Team

| MSSV | Name |
|---|---|
| 3123411122 | Nguyễn Sĩ Huy |
| 3123411328 | Trương Văn Tuấn |
| 3123411258 | Đặng Thành Sơn |
| 3123411045 | Nguyễn Văn Cường |

---

<div align="center">

Made w/ ☕ + Java for OOP Software Development coursework

</div>
