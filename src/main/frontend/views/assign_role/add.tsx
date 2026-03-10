import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { useForm } from "@vaadin/hilla-react-form"
import { useSignal } from "@vaadin/hilla-react-signals"
import { Button, Checkbox, CheckboxGroup, ComboBox, FormLayout, HorizontalLayout, Icon, MultiSelectComboBox, Notification, Select, TextField, VerticalLayout } from "@vaadin/react-components"
import KeycloakUserRecord from "Frontend/generated/com/example/application/data/KeycloakUserRecord"
import UserAppModel from "Frontend/generated/com/example/application/data/UserAppModel"
import {  AdminService, RolesService, UserAppService, UserKeycloakService } from "Frontend/generated/endpoints"
import { log } from "node:console"
import React from "react"
import { useEffect, useState } from "react"
import { data, useNavigate } from "react-router"

export const config: ViewConfig = {
    rolesAllowed: ['ROLE_ADMIN'],
    menu: {exclude: true},
    title: 'User Application Add',
    route: 'add'
}

export default function add() {
  const form = useForm(UserAppModel,{
    onSubmit: async (userApp) => {
      await UserAppService.save(userApp).then(result => {
          Notification.show(`${result.username} telah disimpan`)
          navigate(`/assign_role`)
        })
    }
  })
  const [roles,setRoles] = useState<string[]>([])
  const [usernames,setUsernames] = useState<KeycloakUserRecord[]>([])
  const navigate = useNavigate()
  

  useEffect(
     ()=>{
        const getData = async()=>{
          await UserAppService.addNew().then(result => {
            form.read(result)
          }).catch(()=>Notification.show("ada error"))
          await RolesService.getRoles().then(result => {
            setRoles(result)
          })
          await UserKeycloakService.getKeycloakUserRecords().then(results => {
            setUsernames(results)
            console.log(usernames)
          })
        }
        getData()
     }
    ,[])
  

  return (
    <FormLayout style={{ width: '100%' }} autoResponsive labelsAside>
        
        <Select items={usernames} {...form.field(form.model.username)}  />
        <TextField  {...form.field(form.model.description)} label={'Description'} />
        <CheckboxGroup {...form.field(form.model.roles)}>
            {
              roles.map((value,index) => (
                <Checkbox value={value} key={index} label={value}  />
              ))
            }
        </CheckboxGroup>
        
        <Button onClick={form.submit}>Simpan</Button>
        
    </FormLayout>
  )
}
