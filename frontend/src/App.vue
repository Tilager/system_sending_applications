<script setup>
import { RouterLink, RouterView } from 'vue-router'
import axios from "axios";
import NavComponent from "@/components/NavComponent.vue";
axios.defaults.headers.common = {
  "Content-Type": "application/json"
}

</script>

<template>
  <NavComponent></NavComponent>

  <RouterView />
</template>

<script>
</script>
