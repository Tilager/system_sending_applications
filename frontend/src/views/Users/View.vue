<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Пользователи
          <router-link to="/users/create" class="btn btn-primary float-end">
            Добавить пользователя
          </router-link>
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Логин</th>
            <th>Пароль</th>
            <th>Тип пользователя</th>
            <th>Клиент</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(user, idx) in users" :key="idx">
              <td>{{ user.id }}</td>
              <td>{{ user.login }}</td>
              <td>{{ user.password }}</td>
              <td>{{ types[user.type] }}</td>
              <td v-if="user.client !== null">
                {{ user.client.surname }} {{ user.client.name }} {{ user.client.patronymic }}</td>
              <td v-else></td>
              <td>
                <router-link
                    :to="{ path: '/users/' + user.id + '/edit' }"
                    class="btn btn-success me-1"
                >
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteUser(user.id)" :disabled="user.login === 'admin'">
                  Delete
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import axios from "axios";

  export default {
    name: "users",
    data() {
      return {
        users: [],
        types: {
          user: 'Пользователь',
          admin: 'Администратор'
        }
      }
    },
    mounted() {
      this.getUsers()
    },
    methods: {
      getUsers() {
        axios.get('http://localhost:8081/api/users/all').then(res => {
          this.users = res.data
        })
      },
      deleteUser(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/users/${id}`).then(() => {
            this.getUsers()
          })
        }
      }
    }
  }
</script>