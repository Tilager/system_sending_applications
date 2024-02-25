<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Изменение пользователя
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно обновлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>

        <div class="mb-3">
          <label for="">Логин</label>
          <input v-model="model.user.login"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.user.login.$dirty && v$.model.user.login.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Пароль</label>
          <input v-model="model.user.password"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.user.password.$dirty && v$.model.user.password.$error)}">
        </div>
        <div class="mb-3">
          <label>Тип</label>

          <select class="form-control"
                  :class="{'is-invalid': (v$.model.user.type.$dirty && v$.model.user.type.$error)}"
                  v-model="model.user.type"
          >
            <option value="user">Пользователь</option>
            <option value="admin">Администратор</option>
          </select>
        </div>

        <div class="mb-3">
          <label>Клиент</label>

          <select class="form-control"
                  :class="{'is-invalid': (v$.model.user.client.$dirty && v$.model.user.client.$error)}"
                  @change="setClient" :disabled="model.user.type !== 'user'"
          >
            <option disabled :selected="model.user.client === null" value> Выберите клиента </option>
            <option v-for="client in clients"
                    :value="client.id">
              {{ client.surname }} {{ client.name }} {{ client.patronymic }} </option>
          </select>
        </div>

        <div class="mb-3">
          <button class="btn btn-primary" @click="updateUser" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";
  import {email, integer, minValue, numeric, required} from "@vuelidate/validators";

  export default {
    name: 'usersEdit',
    data() {
      return {
        alert: '',
        model: {
          user: {
            login: '',
            password: '',
            type: '',
            client: null
          }
        },
        clients: []
      }
    },
    mounted() {
      this.getUser(this.$route.params.id)

      axios.get('http://localhost:8081/api/clients/all').then(res => {
        this.clients = res.data
      })
    },
    methods: {
      setClient(event) {
        this.model.user.client = this.clients.find(c => c.id == event.target.value)
      },
      getUser(id) {
        axios.get(`http://localhost:8081/api/users/${id}`).then(res => {
          if (!res.data) {
            alert("Страница не найдена!")
            window.location.replace("/users")
          }
          else {
            this.model.user = res.data
          }
        })
      },
      updateUser() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        if (this.model.user.type !== 'user')
          this.model.user.client = null

        axios.post(`http://localhost:8081/api/users/${this.$route.params.id}`,
            this.model.user)
            .then(res => {
              this.model.user = res.data
              this.alert = 'success'
              this.v$.$reset()
            })
      }
    },
    setup: () => ({ v$: useVuelidate() }),
    validations() {
      return {
        model: {
          user: {
            login: { required },
            password: { required },
            type: { required },
            client: { required: val => {
                return this.model.user.type === 'user' ? val !== null : true
              }}
          }
        }
      }
    }
  }
</script>
