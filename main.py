from machine import Timer
from wifi import Wifi
import umqttsimple
import machine
from machine import PWM
from umqttsimple import MQTTClient
from alldevice import sensor, restart_and_reconnect_network, restart_and_reconnect_MQTT

import ubinascii
import time


class Mqtt(object):
    wifi = Wifi()
    wifi.connect()
    client_id = ubinascii.hexlify(machine.unique_id())
    topic_pub_temp = b'kamar-tidur/temperature'
    topic_pub_hum = b'kamar-tidur/humidity'
    last_message = 0
    message_interval = 5

    def __init__(self, *args, **kwargs):
        super(Mqtt, self).__init__(*args, **kwargs)
        self.client = MQTTClient(self.client_id,
                                 "tailor.cloudmqtt.com",
                                 13898,
                                 'jxvhljee',
                                 'm0q_vUULIQo9')
        try:
            print()
            print("3 / 3 -------------------------------------")
            print("Mnghubungi MQTT Broker..")
            self.client.connect()
            print("MQTT Broker : Terhubung")
            print()
            print("=========Memulai log :")
            while True:
                try:
                    if (time.time() - self.last_message) > self.message_interval:
                        temp, hum = self.read_sensor()
                        self.client.publish(self.topic_pub_temp, temp)
                        self.client.publish(self.topic_pub_hum, hum)
                        print()
                        self.last_message = time.time()
                except:
                    restart_and_reconnect_network()

        except:
            print("koneksi internet atau server terputus")
            restart_and_reconnect_network()

    def read_sensor(self):
        try:
            sensor.measure()
            temp_raw = sensor.temperature()
            hum_raw = sensor.humidity()

            if (isinstance(temp_raw, float) and isinstance(hum_raw, float)) \
                    or (isinstance(temp_raw, int) and isinstance(hum_raw, int)):
                temp = (b'{0:3.1f},'.format(temp_raw))
                hum = (b'{0:3.1f},'.format(hum_raw))

                print("Temp : ", temp_raw, "c")
                print("Hum : ", hum_raw)
                return temp, hum
            else:
                return ('Invalid sensor readings.')
        except:
            restart_and_reconnect_MQTT()
            return ('Failed to read sensor.')

    # def sub_cb(self,topic,msg):
    #     print("topic   ={}".format(topic.decode()))
    #     print("message = {}".format(msg.decode()))

    #     if msg==b"led_on":
    #         led_blue.value(1)
    #     if msg==b"led_off":
    #         led_blue.value(0)

    #     if msg.isdigit():
    #         self.pwm.freq(1024)
    #         self.pwm.duty(1024-int(msg))


Mqtt()



