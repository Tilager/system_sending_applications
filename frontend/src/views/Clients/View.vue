<template>
  <div class="container">
    <div class="card mt-5">
      <div class="card-header">
        <h4>
          Клиенты
          <router-link to="/clients/create" class="btn btn-primary float-end">
            Добавить клиента
          </router-link>
        </h4>
      </div>
      <div class="card-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Номер телефона</th>
            <th>Почта</th>
            <th>Дата рождения</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(client, idx) in clients" :key="idx">
              <td>{{ client.id }}</td>
              <td>{{ client.name }}</td>
              <td>{{ client.surname }}</td>
              <td>{{ client.patronymic }}</td>
              <td>{{ client.phone }}</td>
              <td>{{ client.email }}</td>
              <td>{{ client.birthday }}</td>
              <td>
                <router-link :to="{ path: '/clients/' + client.id + '/edit' }" class="btn btn-success me-1">
                  Edit
                </router-link>
                <button type="button" class="btn btn-danger" @click="deleteClient(client.id)">
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
    name: "clients",
    data() {
      return {
        clients: []
      }
    },
    mounted() {
      this.getClients()
    },
    methods: {
      getClients() {
        axios.get('http://localhost:8081/api/clients/all').then(res => {
          this.clients = res.data
        })
      },
      deleteClient(id) {
        if(confirm("Вы действительно хотите удалить данные?")) {
          axios.delete(`http://localhost:8081/api/clients/${id}`).then(() => {
            this.getClients()
          })
        }
      }
    }
  }
</script>