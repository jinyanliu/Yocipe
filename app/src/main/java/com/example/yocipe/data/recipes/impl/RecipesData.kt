package com.example.yocipe.data.recipes.impl

import com.example.yocipe.R
import com.example.yocipe.model.Recipe

val ingredients_1 = listOf(
    Pair("Blåbär (frysta)", "3 dl"),
    Pair("Banan", "1 styck"),
    Pair("Havregryn", "1 dl"),
    Pair("Havredryck", "3 dl")
)

val ingredients_2 = listOf(
    Pair("Lingon (torrfrysta)", "225 gram"),
    Pair("Yoghurt", "5 dl"),
    Pair("Agavesirap", "3 msk")
)

val ingredients_3 = listOf(
    Pair("Yoghurt Naturell", "2 dl"),
    Pair("Mango Fryst", "3 dl"),
    Pair("Standardmjölk", "1 dl"),
    Pair("Honung Flytande", "2 tsk"),
    Pair("Torkad gurkmeja", "2 krm"),
    Pair("Kardemumma", "0.5 krm")
)

val ingredients_4 = listOf(
    Pair("Hallon", "2 dl"),
    Pair("Banan", "2 styck"),
    Pair("Vaniljyoghurt", "4 dl"),
    Pair("Fruktjuice", "1.5 dl")
)

val ingredients_5 = listOf(
    Pair("smör", "150 g"),
    Pair("mjölk", "5 dl"),
    Pair("jäst för söta degar", "50 g"),
    Pair("vit baksirap", "1 dl"),
    Pair("salt", "0.5 tsk"),
    Pair("stött kardemumma", "2 tsk"),
    Pair("Vetemjöl", "800 g"),
    Pair("smör", "150 g"),
    Pair("strösocker", "1 dl"),
    Pair("kanel", "2 msk"),
    Pair("ägg", "1 styck"),
    Pair("Pärlsocker", "50 ml")
)

val ingredients_6 = listOf(
    Pair("färsk jäst", "15 g"),
    Pair("ljummet vatten", "3 dl"),
    Pair("strösocker", "2 tsk"),
    Pair("flingsalt", "1 tsk"),
    Pair("olivolja", "2 msk"),
    Pair("durumvete", "6 dl"),
    Pair("vetemjöl special", "5 dl"),
    Pair("crème fraiche", "2.5 dl"),
    Pair("Västerbottensost, riven", "2 dl"),
    Pair("olivolja", "3 msk"),
    Pair("Salt", "1 tsk"),
    Pair("Svartpeppar", "1 tsk"),
    Pair("löjrom", "150 g"),
    Pair("rödlök", "1 styck"),
    Pair("dill", "1 dl"),
    Pair("crème fraiche", "1 dl"),
    Pair("Västerbottensost, riven", "1 dl"),
    Pair("citro", "0.5 styck"),
    Pair("kapris", "4 msk")
)

val instructions_1 = listOf(
    "1. Mixa samman blåbär, banan, havregryn och havredryck.",
    "2. Späd eventuellt med mer havredryck för att få en bra konsistens."
)

val instructions_2 = listOf(
    "1. Lägg alla ingredienser i en blender.",
    "2. Mixa."
)

val instructions_3 = listOf(
    "1. Mango lassi: Mixa yoghurt, mango, mjölk, honung, gurkmeja och kardemumma i en blender.",
    "2. Topping: Finhacka mandlar och mynta och blanda med kokosflingor och kardemumma.",
    "3. Häll upp mango lassin i glas och toppa med mandelsmulet. Drick och njut!"
)

val instructions_4 = listOf(
    "1. Lägg alla ingredienser i en blender.",
    "2. Mixa."
)

val instructions_5 = listOf(
    "1. Värm mjölken till 37°C (fingervarmt).",
    "2. Smula ner jästen i en degskål på 3-4 l. Häll över mjölken och rör om. Tillsätt matfettet i bitar, socker eller sirap, salt och eventuell kardemumma.",
    "3. Mät upp mjölet. Häll det luftigt direkt ur påsen i ett litermått. Skaka inte måttet. Tillsätt mjölet men spar ½ dl till utbakningen.",
    "4. Arbeta degen kraftigt, cirka 5 minuter med maskin eller 10 minuter för hand, tills den känns smidig.",
    "5. Låt degen jäsa övertäckt med bakduk cirka 30 minuter.",
    "6. Arbeta ner degen med maskin eller knåda den lätt på mjölat bakbord. Dela degen i 4 delar. Kavla ut varje del till en avlång kaka.",
    "7. Rör ihop ingredienserna till valfri fyllning och bred ett tunt lager på kakan. Rulla ihop till en rulle. Fler utbakningsförslag, se längre ner.",
    "8. Låt bröden svalna på galler under bakduk.",
    "9. Skär rullen i 10 delar och lägg dem med snittytan upp i pappersformar eller på plåtar täckta med bakplåtspapper. Låt jäsa under bakduk cirka 40 minuter. Värm ugnen till 225-250°C. Pensla med ägg och grädda sedan bullarna mitt i ugnen, 5-10 minuter."
)

val instructions_6 = listOf(
    "1. Smula jästen i en bunke och lös upp i ljummet vattnet.",
    "2. Rör ner socker, flingsalt och olivolja. Tillsätt durumvete och vetemjöl lite i taget och knåda degen tills den är elastisk, i maskin eller för hand.",
    "3. Strö över lite vetemjöl och jäs degen under bakduk till dubbel storlek i ca 45 minuter. ",
    "4. Dela degen i fyra och kavla ut tunt på ett mjölat bakbord.",
    "5. Rör ihop ostcrèmen och bred ett jämnt lager över pizzorna. ",
    "6. Ringla över olivolja, strö på salt och peppra. ",
    "7. Grädda pizzan i mitten av ugnen ca 5 minuter. ",
    "8. Ta ut pizzan och toppa med löjrom, rödlök, dill, crème fraiche, kapris och smulad Västerbottensost. ",
    "9. Till sist, riv över lite citronskal."
)

val recipe1 = Recipe(
    id = "dc523f0ed25c",
    imageId = R.drawable.image_recipe_1,
    name = "Blåbärssmoothie",
    ingredients = ingredients_1,
    instructions = instructions_1,
    servings = "2 glas"
)

val recipe2 = Recipe(
    id = "7446d8dfd7dc",
    imageId = R.drawable.image_recipe_2,
    name = "Lingonlassi",
    ingredients = ingredients_2,
    instructions = instructions_2,
    servings = "2 glas"
)

val recipe3 = Recipe(
    id = "7356d8afd4dc",
    imageId = R.drawable.image_recipe_3,
    name = "Mangolassi",
    ingredients = ingredients_3,
    instructions = instructions_3,
    servings = "2 glas"
)

val recipe4 = Recipe(
    id = "1055d8acd5dc",
    imageId = R.drawable.image_recipe_4,
    name = "Hallonsmoothie",
    ingredients = ingredients_4,
    instructions = instructions_4,
    servings = "2 glas"
)

val recipe5 = Recipe(
    id = "3056g8Hcd5dc",
    imageId = R.drawable.image_recipe_5,
    name = "Kanelbullar",
    ingredients = ingredients_5,
    instructions = instructions_5,
    servings = "40 bullar"
)

val recipe6 = Recipe(
    id = "1056g3Htd0ac",
    imageId = R.drawable.image_recipe_6,
    name = "Pizza Bianca med Västerbottensost® och löjrom",
    ingredients = ingredients_6,
    instructions = instructions_6,
    servings = "4 pizzor"
)

val recipes: List<Recipe> =
    listOf(
        recipe6,
        recipe5,
        recipe3,
        recipe1,
        recipe2,
        recipe4
    )