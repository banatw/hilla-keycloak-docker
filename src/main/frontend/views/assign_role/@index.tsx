import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { AutoGrid, AutoGridRef } from "@vaadin/hilla-react-crud";
import { useSignal } from "@vaadin/hilla-react-signals";
import { Button, GridColumn, HorizontalLayout, Item, MenuBar, Notification, TextField, VerticalLayout } from "@vaadin/react-components";
import UserApp from "Frontend/generated/com/example/application/data/UserApp";
import UserAppModel from "Frontend/generated/com/example/application/data/UserAppModel";
import { AdminService, UserAppService } from "Frontend/generated/endpoints";
import React from "react";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";

export const config: ViewConfig = {
    rolesAllowed: ['ROLE_ADMIN'],
    menu: {title: 'Assign Role'},
    title: 'Assign User Role'
}

export default function UsersIndex() {
    const menu = [
        {text: 'Edit'},
        {text: 'Delete'}
    ]

    const autoGridRef = React.useRef<AutoGridRef>(null)
    const actionHandler = async(action: string,item: UserApp)=>{
        if(action === 'Edit') {
            nav(`/assign_role/${item.username}`)
        }
        if(action === 'Delete') {
            await UserAppService.delete(item).then(result=>{
                Notification.show(`${result.username} telah dihapus`)
                autoGridRef.current?.refresh()
            })
        }
    }
    
    const nav = useNavigate()
    const actionRenderer = ({item: userApp} : {item: UserApp})=>{
        return(
            <MenuBar items={menu}  onItemSelected={(e)=>actionHandler(e.detail.value.text,userApp)} />
        )
    }

    const usernameRenderer = ({item: userapp} : {item: UserApp})=>{
        return (
            <span>{userapp.username} : {userapp.description}</span>
        )
    }

    return(
        <VerticalLayout>
            
            <Button onClick={()=>nav(`/assign_role/add`)}>Add</Button>
            <AutoGrid
                model={UserAppModel}
                service={UserAppService}
                ref={autoGridRef}
                columnOptions={{
                    description: {
                        renderer: usernameRenderer
                    }
                }}
                customColumns={[
                    <GridColumn key={'edit'} renderer={actionRenderer} header='Action'  />
                ]}
            >
            </AutoGrid> 
        </VerticalLayout>
    )
}