<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const airports = ref([]);

const selectedOrigin = ref("");
const selectedDestination = ref("");

const earliestDeparture = ref("");
const latestArrival = ref("");

const flightPaths = ref([]);

function formatLocalDateTime(date) {
  const pad = (n) => n.toString().padStart(2, "0");
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(
    date.getDate()
  )}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
}

onMounted(async () => {
  earliestDeparture.value = formatLocalDateTime(new Date());

  try {
    const response = await axios.get("/api/flights/airports");
    airports.value = response.data;
  } catch (error) {
    console.error("Failed to fetch airports: ", error);
  }
});

async function queryFlights() {
  if (
    !selectedOrigin.value ||
    !selectedDestination.value ||
    !earliestDeparture.value
  ) {
    alert("Origin, destination, and earliest departure are required!");
    return;
  }

  const params = {
    origin: selectedOrigin.value,
    destination: selectedDestination.value,
    earliestTime: new Date(earliestDeparture.value).toISOString(),
  };

  if (latestArrival.value) {
    params.latestTime = new Date(latestArrival.value).toISOString();
  }

  try {
    const response = await axios.get("/api/flights/flightPaths", { params });
    flightPaths.value = response.data;
    console.log("Flights: ", flightPaths.value);
  } catch (error) {
    console.error("Failed to fetch flights: ", error);
  }
}
</script>

<template>
  <div id="body">
    <div id="query-card" class="card">
      <h1 id="query-header">Query for flights:</h1>
      <div id="attributes-container">
        <div class="attributes-row">
          <label for="origin-select">Origin:</label>
          <select id="origin-select" v-model="selectedOrigin">
            <option disabled value=""></option>
            <option
              v-for="airport in airports"
              :key="airport.id"
              :value="airport.airportCode"
            >
              {{ airport.airportCode }} - {{ airport.country }},
              {{ airport.city }}
            </option>
          </select>
          <label for="destination-select">Destination:</label>
          <select id="destination-select" v-model="selectedDestination">
            <option disabled value=""></option>
            <option
              v-for="airport in airports"
              :key="airport.id"
              :value="airport.airportCode"
            >
              {{ airport.airportCode }} - {{ airport.country }},
              {{ airport.city }}
            </option>
          </select>
        </div>
        <div class="attributes-row">
          <label for="earliest-departure">Earliest departure time:</label>
          <input
            id="earliest-departure"
            type="datetime-local"
            v-model="earliestDeparture"
          />
          <label for="latest-arrival">Latest arrival time (optional):</label>
          <input
            id="latest-arrival"
            type="datetime-local"
            v-model="latestArrival"
          />
        </div>
      </div>
      <button
        id="search-flights-button"
        class="main-button"
        @click="queryFlights"
      >
        Search Flights
      </button>
    </div>
    <div id="flights-card" class="card" v-if="0 < flightPaths.length">
      <div
        class="flight-path"
        v-for="flightPath of flightPaths"
        :key="flightPath.id"
      >
        {{ flightPath.flights.length }} {{ flightPath.totalTravelTime }}
      </div>
    </div>
  </div>
</template>

<style scoped>
#body {
  min-height: 100vh;
  width: 100vw;
  margin: 0;
  padding: 0;
  overflow-y: auto;
  overflow-x: hidden;
  background-image: url("@/assets/background-evening-clouds.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  display: flex;
  justify-content: center;
  box-sizing: border-box;
  align-items: center;
  flex-direction: column;
}

.card {
  background-color: rgba(255, 255, 255, 0.6);
  padding: 2rem;
  margin: 1rem;
  border-radius: 1rem;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}

#attributes-container {
  display: flex;
  flex-direction: column;
}

#attributes-container label {
  padding: 0 1rem;
}

.attributes-row {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 0.5rem;
}

/* main-button CSS from https://getcssscan.com/css-buttons-examples */
.main-button {
  appearance: none;
  background-color: transparent;
  border: 2px solid #1a1a1a;
  border-radius: 15px;
  box-sizing: border-box;
  color: #3b3b3b;
  cursor: pointer;
  display: inline-block;
  font-family: Roobert, -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica,
    Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  font-size: 16px;
  font-weight: 600;
  line-height: normal;
  margin: 0;
  min-height: 60px;
  min-width: 0;
  outline: none;
  padding: 8px 16px;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  will-change: transform;
}

.main-button:disabled {
  pointer-events: none;
}

.main-button:hover {
  color: #fff;
  background-color: #1a1a1a;
  box-shadow: rgba(0, 0, 0, 0.25) 0 8px 15px;
  transform: translateY(-2px);
}

.main-button:active {
  box-shadow: none;
  transform: translateY(0);
}
</style>
