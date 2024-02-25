<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        Добавление клиента
      </div>
      <div class="card-body">
        <div class="alert alert-success" role="alert" v-if="alert === 'success'">
          Данные успешно добавлены!
        </div>
        <div class="alert alert-danger" role="alert" v-if="alert === 'danger'">
          Проверьте все поля!
        </div>

        <div class="mb-3">
          <label for="">Фамилия</label>
          <input v-model="model.client.surname"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.client.surname.$dirty && v$.model.client.surname.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Имя</label>
          <input v-model="model.client.name"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.client.name.$dirty && v$.model.client.name.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Отчество</label>
          <input v-model="model.client.patronymic"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.client.patronymic.$dirty && v$.model.client.patronymic.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Номер телефона</label>
          <input v-model="model.client.phone"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.client.phone.$dirty && v$.model.client.phone.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Почта</label>
          <input v-model="model.client.email"
                 type="text" class="form-control"
                 :class="{'is-invalid': (v$.model.client.email.$dirty && v$.model.client.email.$error)}">
        </div>
        <div class="mb-3">
          <label for="">Дата рождения</label>
          <input v-model="model.client.birthday"
                 type="date" class="form-control"
                 :class="{'is-invalid': (v$.model.client.birthday.$dirty && v$.model.client.birthday.$error)}">
        </div>
        <div class="mb-3">
          <button class="btn btn-primary" @click="saveClient" type="button">Сохранить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import useVuelidate from "@vuelidate/core";
  import {email, required} from "@vuelidate/validators";

  export default {
    name: 'clientCreate',
    data() {
      return {
        alert: '',
        model: {
          client: {
            name: '',
            surname: '',
            patronymic: '',
            phone: '',
            email: '',
            birthday: null
          }
        }
      }
    },
    methods: {
      saveClient() {
        if (this.v$.$invalid) {
          this.v$.$touch()
          this.alert = 'danger'
          return
        }

        axios.post("http://localhost:8081/api/clients/create",
            this.model.client)
            .then(res => {
              this.model.client = {
                name: '',
                surname: '',
                patronymic: '',
                phone: '',
                email: '',
                birthday: null
              }

              this.alert = 'success'
              this.v$.$reset()
            })
      }
    },
    setup: () => ({ v$: useVuelidate() }),
    validations() {
      return {
        model: {
          client: {
            name: { required },
            surname: { required },
            patronymic: { required },
            phone: { required, phone: value => {
                return /^(^8|7|\+7)((\d{10})|(\s\(\d{3}\)\s\d{3}\s\d{2}\s\d{2}))$/.test(value)
              }},
            email: { required, email },
            birthday: { required, minValue: value => {
                let age = Math.abs(new Date(Date.now() - new Date(value)).getUTCFullYear() - 1970)
                return age >= 18 && age <= 100
              }}
          }
        }
      }
    }
  }
</script>
