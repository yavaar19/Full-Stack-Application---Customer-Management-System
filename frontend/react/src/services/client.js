import axios from "axios";

export const getCustomers = async () =>{

    try {

        return await axios.get(`${ import.meta.env.VITE_API_BASE_URL }/api/v1/customers`, {
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            }
        })

    }catch (e){

        throw e;

    }

}

export const saveCustomer = async (customer) => {

    try {

        return await axios.post(`${ import.meta.env.VITE_API_BASE_URL }/api/v1/customers`,
            customer, {
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                }
            }
            )

    } catch (e){

        throw e;

    }

}

export const deleteCustomer = async (id) => {

    try {

        return await axios.delete(`${ import.meta.env.VITE_API_BASE_URL }/api/v1/customers/${id}`,

        )

    } catch (e){

        throw e;

    }

}

export const updateCustomer = async (id, update) => {

    try {

        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            update
        )

    } catch (e) {

        throw e;

    }

}