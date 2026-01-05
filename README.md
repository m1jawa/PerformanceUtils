# PerformanceUtils

**PerformanceUtils** is a Minecraft plugin for **PaperMC** servers that notifies server administrators of performance issues. Notifications are sent to **server chat**, **console**, and **Discord** via Discord Webhook.

The plugin tracks server load status and includes features such as:

- Configurable minimum TPS duration for determining server status  
- Automatic diagnostics in critical status, showing:
  - number of players,
  - number of entities,
  - number of loaded chunks  
- Duration in the last status and maximum TPS drop

---
<br>

## Features

- Automatic server performance monitoring  
- Notifications in chat, console, and Discord (via Webhook)  
- Configurable check intervals and TPS thresholds  
- Displays server statistics during critical performance issues  
- Free to use on any server, including commercial servers

---
<br>

## Installation

You can install PerformanceUtils in one of the following ways:

1. **Download the `.jar` file**
  - Move it into the `plugins` folder on your PaperMC server.
  - Restart the server or use a safe `/reload`.
  - Configure `config.yml` as needed.

2. **Build from source using Maven**
  - Clone the repository:
    ```bash
    git clone https://github.com/YourUser/PerformanceUtils.git
    ```  
  - Navigate into the project folder:
    ```bash
    cd PerformanceUtils
    ```  
  - Build the plugin:
    ```bash
    mvn clean package
    ```  
  - The compiled `.jar` will be located in the `target/` folder and will be named something like `PerformanceUtils-<version>.jar`.
  - Move this `.jar` into your server's `plugins` folder and restart the server.

> The plugin runs automatically and checks performance every N seconds, depending on the configuration. There are no manual commands.

---
<br>

## Configuration

See the full config file [config.yml](./src/main/resources/config.yml) for reference.

---
<br>

## License

This project is licensed under the NC-MIT License (No Commercial Distribution).

✔ Free to use on commercial servers<br>
✔ Free to modify and redistribute<br>
✔ Voluntary donations are permitted<br>
❌ Selling the plugin or derivative works is prohibited

For full license text, see [LICENSE](./LICENSE).

Disclaimer:
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Use of the Software is entirely at the user's own risk.