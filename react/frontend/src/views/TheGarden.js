import { useState, useEffect } from "react";
import PlantCard from "../components/PlantCard";
import SearchForm from "../components/SearchForm";
import DropdownFilter from "../components/DropDownFilter";

import { CardDeck, Container, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

function TheGarden() {
  const axios = require("axios");

  const [categories, setCategories] = useState([]);
  const [plants, setPlants] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState([]);
  const [subcategoryFilter, setSubcategoryFilter] = useState([]);

  const getPlants = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/plants",
      withCredentials: true,
    })
      .then((res) => {
        if (res.status == 200) {
          console.log(res);
          setPlants(res.data);
        } else throw Error(res.status);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleFilter = (id) => {
    console.log(id);
    let newArray = [];
    if (!categoryFilter.includes(id)) {
      newArray = [...categoryFilter, id];
    } else {
      newArray = categoryFilter.filter((oldId) => id !== oldId);
    }
    setCategoryFilter(newArray);
    console.log(newArray);
  };

  const getCategories = () => {
    axios.get("http://localhost:8080/categories").then((res) => {
      console.log(res.data);
      setCategories(res.data);
    });
  };

  useEffect(() => {
    getCategories();
    getPlants();
  }, []);


  // const mappedPlants = plants.map((plant) => {
  //   return (
  //     <Link to={"/garden/detail/" + plant.id}>
  //       <PlantCard key={plant.id} plant={plant} />
  //     </Link>
  //   );
  // });

  return (
    <div>
      <SearchForm />
      <Container>
        <Link to={"/garden/new"}>
          <Button variant="info" >
            {" "}
            Pridaj novú rastlinu{" "}
          </Button>{" "}
        </Link>
      </Container>
      <DropdownFilter
        categories={categories}
        categoryFilter={categoryFilter}
        handleFilter={handleFilter}
      />
      <Container>
        <CardDeck>
          {plants
            .filter((plant) =>
              categoryFilter.length > 0
                ? categoryFilter.includes(plant.subcategoryDto.category.id)
                : plant
            )
            .map((plant) => (
              <Link key={plant.id} to={"/garden/detail/" + plant.id}>
                <PlantCard key={plant.id} plant={plant} />
              </Link>
            ))}
        </CardDeck>
      </Container>
    </div>
  );
}

export default TheGarden;
