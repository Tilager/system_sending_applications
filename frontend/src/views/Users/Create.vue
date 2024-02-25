<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Добавление пользователя
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно добавлены!
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
                  @change="setClient"
                  :disabled="model.user.type !== 'user'"
          >
            <option disabled selected value> Выберите клиента </option>
            <option v-for="client in clients"
                    :value="client.id"> {{ client.surname }} {{ client.name }} {{ client.patronymic }} </option>
          </select>
        </div>
        <div class="mb-3">
          <button class="btn btn-primary" @click="saveUser" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import useVuelidate from "@vuelidate/core";
  import {email, integer, minValue, numeric, required} from "@vuelidate/validators";
  import axios from "axios";

  export default {
    name: 'userCreate',
    data() {
      return {
        alert: '',
        model: {
          user: {
            login: '',
            password: '',
            type: 'user',
            client: null
          }
        },
        clients: []
      }
    },
    mounted() {
      axios.get('http://localhost:8081/api/clients/all').then(res => {
        this.clients = res.data
      })
    },
    methods: {
      setClient(event) {
        this.model.user.client = this.clients.find(c => c.id == event.target.value)
      },
      saveUser() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post("http://localhost:8081/api/users/create",
            this.model.user)
            .then(res => {
              this.model.user = {
                login: '',
                password: '',
                type: 'user',
                client: null
              }

              this.alert = 'success'
              this.v$.$reset()
            }).catch(err => {
                if(err.response.status === 409) {
                  alert(err.response.data)
                }
                else {
                  console.error(err.response.data)
                }
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
