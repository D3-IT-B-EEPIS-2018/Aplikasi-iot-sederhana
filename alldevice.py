from machine import Pin
import machine, time
import dht

status = False

def restart_and_reconnect_network():
    print('Gagal terkoneksi ke MQTT broker. Reconnecting...')
    time.sleep(5)
    machine.reset()


def restart_and_reconnect_MQTT():
    print('Gagal mencari DHT11. searching...')
    time.sleep(5)
    machine.reset()


print()
print("1 / 3 -------------------------------------")
print("Memeriksa Sensor..")
for i in (0, 2, 4, 5, 12, 13, 14, 15, 16):  # D1 Mini only
    sensor = dht.DHT11(Pin(i))
    print("Memeriksa Pin = ",i)
    try:
        sensor.measure()
        print("Device : Sensor DHT11 Ready di Pin ",i)
        status = True
        break
    except:
        continue

if status is not True:
    restart_and_reconnect_MQTT()
# try:
#     sensor.measure()
#     print("Device : Sensor DHT11 Ready di Pin 2")
# except:
#     restart_and_reconnect_MQTT()
