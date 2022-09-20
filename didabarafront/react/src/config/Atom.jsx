import { atom, selector } from "recoil";

export const User = {
  id: "",
  password: "",
  token: "",
};
export const userState = atom({
  key: "userState",
  default: User | null,
});
export const loginState = atom({
  key: "loginState",
  default: false,
});

export const menuState = atom({
  key: "menuState",
  default: "Listing",
});
export const didabaraItemState = atom({
  key: "item",
  default: [],
});
export const myListOrJoinList = atom({
  key: "whichList",
  default: "",
});

export const didabaraState = atom({
  key: "didabara",
  default: {
    create: [{}],
    join: [{}],
  },
});

export const categoryItem = atom({
  key: "categoryId",
  default: [],
});

export const didabaraSelector = selector({
  key: "didabaraSelector",
  get: ({ get }) => {
    const menu = get(menuState);
    const items = get(didabaraItemState);
    const list = get(myListOrJoinList);
    if (items.length === 0) return null;

    const matchedItems = items.filter((item) => item.category === list);
    switch (menu) {
      case "Listing":
        if (matchedItems.length) {
          const listing = matchedItems.filter(
            (item) => Date.parse(item.expiredDate) > Date.now()
          );

          return listing.length ? listing : null;
        } else {
          return null;
        }

      case "Out Dated":
        if (matchedItems.length) {
          const outDated = matchedItems.filter(
            (item) => Date.parse(item.expiredDate) < Date.now()
          );
          return outDated.length ? outDated : null;
        } else {
          return null;
        }

      case "All List":
        if (matchedItems.length) {
          return matchedItems;
        } else {
          return null;
        }
    }
  },
});
