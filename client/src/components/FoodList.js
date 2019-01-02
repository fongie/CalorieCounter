import React from 'react';
import { ListGroup, ListGroupItem } from 'react-bootstrap';

const FoodList = (props) => {
    //console.log(props.data[0]);
    return (
        <ListGroup>
            {
                props.data != null ? props.data.map(
                    obj =>
                    <ListGroupItem
                        key={obj.id}
                    >
                        {obj.name} : {obj.calories} : {obj.protein}
                    </ListGroupItem>
                ) : null
            }
        </ListGroup>
    );
}

export default FoodList;
