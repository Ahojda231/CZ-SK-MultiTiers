# ⚔️ CZ/SK-MultiTIers v1.0.0

Komunitní československá edice Minecraft módu **Tiers** pro zobrazení tierlistů, hráčských statistik a ranků přímo ve hře.

- **Autor:** **Saukr**
- **Původní projekt / Fork:** Fork původního módu [PvPTiers/Tiers](https://github.com/PvPTiers/Tiers) od **Flavio6561**
- **Verze módu:** 1.0.0
- **Podporovaný modloader:** Fabric

---

## 📥 Ke stažení (Všech 16 verzí)

Všechny zkompilované .jar soubory jsou k dispozici přímo ve složce [jars/](jars/):

| Verze Minecraftu | Minimální Java | Odkaz na stažení JAR |
| :--- | :---: | :--- |
| **26.2** (Nejnovější) | Java 25 | [CZ-SK-MultiTIers-1.0.0+26.2.jar](jars/CZ-SK-MultiTIers-1.0.0+26.2.jar) |
| **26.1.2** | Java 25 | [CZ-SK-MultiTIers-1.0.0+26.1.2.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.2.jar) |
| **26.1.1** | Java 25 | [CZ-SK-MultiTIers-1.0.0+26.1.1.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.1.jar) |
| **26.1** | Java 25 | [CZ-SK-MultiTIers-1.0.0+26.1.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.jar) |
| **1.21.11** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.11.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.11.jar) |
| **1.21.10** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.10.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.10.jar) |
| **1.21.9** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.9.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.9.jar) |
| **1.21.8** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.8.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.8.jar) |
| **1.21.7** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.7.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.7.jar) |
| **1.21.6** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.6.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.6.jar) |
| **1.21.5** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.5.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.5.jar) |
| **1.21.4** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.4.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.4.jar) |
| **1.21.3** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.3.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.3.jar) |
| **1.21.2** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.2.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.2.jar) |
| **1.21.1** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.1.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.1.jar) |
| **1.21** | Java 21 | [CZ-SK-MultiTIers-1.0.0+1.21.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.jar) |

---

## 🏆 Podporované žebříčky (6 systémů)

Mód podporuje současné zobrazení jak mezinárodních, tak československých tierlistů s vlastním přepínáním v konfiguraci (/tiers -config):

### Globální:
1. **MCTiers** (mctiers.com)
2. **PvPTiers** (pvptiers.com)
3. **Subtiers** (subtiers.net)

### CZ/SK Scéna:
4. **CZSKTiers.com** (Oficiální CZ/SK žebříček přes Supabase REST API)
5. **CZSK Tiers (b0tfleyz)** (Komunitní žebříček)
6. **CZSK Subtiers (b0tfleyz)** (CZ/SK sub-žebříček)

---

## 📂 Struktura repozitáře

- [jars/](jars/) – Hotové zkompilované .jar soubory připravené k vložení do složky .minecraft/mods/.
- [sources/](sources/) – Zdrojové kódy rozdělené podle verzí Minecraftu a jejich API:
  - [sources/26.2/](sources/26.2/) – Zdrojový kód pro Minecraft 26.2
  - [sources/26.1.x/](sources/26.1.x/) – Zdrojový kód pro 26.1, 26.1.1 a 26.1.2
  - [sources/1.21.11/](sources/1.21.11/) – Zdrojový kód pro 1.21.11
  - [sources/1.21.9-1.21.10/](sources/1.21.9-1.21.10/) – Zdrojový kód pro 1.21.10 a 1.21.9
  - [sources/1.21.6-1.21.8/](sources/1.21.6-1.21.8/) – Zdrojový kód pro 1.21.8, 1.21.7 a 1.21.6
  - [sources/1.21.5/](sources/1.21.5/) – Zdrojový kód pro 1.21.5
  - [sources/1.21.2-1.21.4/](sources/1.21.2-1.21.4/) – Zdrojový kód pro 1.21.4, 1.21.3 a 1.21.2
  - [sources/1.21-1.21.1/](sources/1.21-1.21.1/) – Zdrojový kód pro 1.21.1 a 1.21

---

## 🛠️ Jak sestavit ze zdrojových kódů

Přejděte do složky příslušné verze (např. sources/1.21.4) a spusťte:

\\\ash
./gradlew build
\\\
Výsledný JAR se vytvoří ve složce uild/libs/.
