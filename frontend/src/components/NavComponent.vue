<script setup>

import {RouterLink} from "vue-router";
</script>

<template>
  <header>
    <div class="wrapper">
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
          <h4 class="text-white" v-if="currentUser !== null && currentUser.type === 'user'"
              >{{ currentUser.client.surname }} {{ currentUser.client.name }} {{ currentUser.client.patronymic }}
          </h4>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
              <li v-for="link in guestLinks" v-if="!currentUser">
                <router-link class="nav-link" :to="link.url">{{ link.name }}</router-link>
              </li>
              <li v-for="link in adminLinks" v-else-if="currentUser.type==='admin'">
                <router-link class="nav-link" :to="link.url">{{ link.name }}</router-link>
              </li>
              <li v-for="link in userLinks" v-else-if="currentUser.type==='user'">
                <router-link class="nav-link" :to="link.url">{{ link.name }}</router-link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </header>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  data() {
    return {
      guestLinks: [
        {url: '/register', name: 'Зарегистрироваться'},
        {url: '/login', name: 'Войти'}
      ],
      adminLinks: [
        {url: '/teachers', name: 'Преподаватели'},
        {url: '/courses', name: 'Курсы'},
        {url: '/groups', name: 'Группы'},
        {url: '/clients', name: 'Клиенты'},
        {url: '/users', name: 'Пользователи'},
        {url: '/applications', name: 'Заявки'},
        {url: '/login?logout', name: 'Выйти'}
      ],
      userLinks: [
        {url: '/application', name: 'Отправить заявку'},
        {url: '/login?logout', name: 'Выйти'}
      ]
    }
  },
  computed: mapGetters(['currentUser'])
}
</script>