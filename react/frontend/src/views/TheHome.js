import {Container, CardDeck } from "react-bootstrap";
import { useState, useEffect } from "react";
import TheSpinner from "../components/TheSpinner";
import SensorCard from "../components/SensorCard";

function TheHome() {
  const axios = require("axios");
  const [temperature, setTemperature] = useState(null);
  const [humidity, setHumidity] = useState(null);
  const [pressure, setPressure] = useState(null);
  const [rain, setRain] = useState(null);

  const fetchTemperature = () => {
    axios.get("http://localhost:8080/sensors/temperature").then((res) => {
      console.log(res.data);
      setTemperature(res.data);
    });
  };

  const fetchPressure = () => {
    axios.get("http://localhost:8080/sensors/pressure").then((res) => {
      console.log(res.data);
      setPressure(res.data);
    });
  };

  const fetchHumidity = () => {
    axios.get("http://localhost:8080/sensors/humidity").then((res) => {
      console.log(res.data);
      setHumidity(res.data);
    });
  };

  const refreshData = () => {
    axios.get("http://localhost:8080/sensors/request").then((res) => {
      fetchHumidity();
      fetchPressure();
      fetchTemperature();
    });
  };

  const fetchRain = () => {
    axios.get("http://localhost:8080/sensors/rain").then((res) => {
      console.log(res.data);
      setRain(res.data);
    });
  };
  
  useEffect(() => {
    fetchTemperature();
    fetchHumidity();
    fetchPressure();
    fetchRain();
  }, []);

  if (temperature == null || humidity == null || pressure == null) {
    return <TheSpinner></TheSpinner>;
  }

  return (
    <div>
      <Container className="pt-5">
        <CardDeck>

          <SensorCard key="temperature" sensor={temperature} name="Teplota" text={temperature.value + " °C"} picture="assets/sensors/teplomer.png" ></SensorCard>
          <SensorCard key="pressure" sensor={pressure} name="Tlak vzduchu" text={pressure.value + " hPa"} picture="assets/sensors/pressure.png" ></SensorCard>
          <SensorCard key="humidity" sensor={humidity} name="Vlhkosť vzduchu" text={humidity.value + " %"}  picture="assets/sensors/humiditySmaller.png" ></SensorCard>

        </CardDeck>
        <br />
        <button
          type="button"
          class="btn btn-warning"
          onClick={() => refreshData()}
        >
          Aktualizuj
        </button>
      </Container>
      <br />

      {rain != null ? (
        <Container>
          <CardDeck>
            <SensorCard key="rain" sensor={rain} name="Zrážky" text={rain.raining ? "Prší" : "V tomto okamžiku neprší"}  picture={ rain.raining ? "assets/sensors/rain.jpg" : "assets/sensors/sun.jpg"} ></SensorCard>
          </CardDeck>
        </Container>
      ) : (
       null
      )}
    </div>
  );
}

export default TheHome;
